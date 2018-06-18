import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { Redirect } from 'react-router-dom'

import Divider from 'material-ui/Divider'
import ContentAdd from 'material-ui/svg-icons/content/add'
import FloatingActionButton from 'material-ui/FloatingActionButton'
import MenuItem from 'material-ui/MenuItem'
import RaisedButton from 'material-ui/RaisedButton'
import SelectField from 'material-ui/SelectField'
import DatePickerDialog from 'material-ui/DatePicker/DatePickerDialog'
import TimePickerDialog from 'material-ui/TimePicker/TimePickerDialog'
import { pink500, grey400 } from 'material-ui/styles/colors'

import DateTimePicker from 'material-ui-datetimepicker'
import Loader from 'react-loader'
import moment from 'moment'
import last from 'lodash/last'

import { sortByDate } from '../../../../../utils/date'

import PageBase from '../../common/PageBase'

import globalStyles from '../../../../../styles'
import accountService from '../../../../../services/accountService'
import activityService from '../../../../../services/activityService'
import loginService from '../../../../../services/loginService'
import planService from '../../../../../services/planService'
import notificationService from '../../../../../services/notificationService'
import { PLAN_FORM } from '../../../../../config/routes'

const styles = {
  toggleDiv: {
    maxWidth: 300,
    marginTop: 40,
    marginBottom: 5,
  },
  toggleLabel: {
    color: grey400,
    fontWeight: 100,
  },
  buttons: {
    marginTop: 30,
    float: 'right',
  },
  saveButton: {
    marginLeft: 5,
  },
  listCheckbox: {
    marginTop: '20px',
  },
  appointmentList: {
    position: 'relative',
  },
  floatingActionButton: {
    margin: 0,
    top: 'auto',
    right: 200,
    bottom: 10,
    left: 'auto',
    position: 'absolute',
  },
}

class PlanForm extends Component {
  state = {
    didLoad: false,
    redirect: false,

    plan: {
      customer: null,
      appointments: [],
    },
    activities: [],
    customers: [],
  }

  componentDidMount() {
    const id = this.getRouteId()

    const activitiesPromise = activityService.all()
    const customersPromise = accountService.getCustomers()
    const trainersPromise = accountService.getTrainers()
    const planPromise = id ? planService.get(id) : Promise.resolve(this.state.plan)

    Promise.all([
      activitiesPromise,
      customersPromise,
      trainersPromise,
      planPromise,
    ])
      .then((data) => {
        const [activities, customers, trainers, plan] = data

        const mapTrainers = _trainers => _trainers
          .map(trainer => trainers.find(t => t.id === trainer.id))

        const mapActivities = _activities => _activities
          .map(activity => ({
            ...activity,
            trainers: mapTrainers(activity.trainers),
          }))

        const activitiesWithMappedTrainers = mapActivities(activities)

        const mapAppointments = appointments => appointments
          .map(appointment => ({
            ...appointment,
            activity: activitiesWithMappedTrainers.find(activity => activity.id === appointment.activity.id),
            trainer: mapTrainers([appointment.trainer])[0],
          }))

        const user = loginService.getUser()
        const getCustomer = () => {
          if (loginService.isCustomer()) {
            return plan.customer ? customers.find(customer => customer.id === plan.customer.id) : customers.find(customer => customer.id === user.accountId)
          }
          return plan.customer ? customers.find(customer => customer.id === plan.customer.id) : this.state.plan.customer // substitui a instância pela instância do `getCustomers`
        }

        this.setState({
          plan: {
            ...plan,
            customer: getCustomer(),
            appointments: plan.appointments ? mapAppointments(plan.appointments) : this.state.plan.appointments, // substitui a instância pela instância do `all`
          },
          activities: activitiesWithMappedTrainers,
          customers,
          didLoad: true,
        })
      })
      .catch((err) => {
        console.error(err)
        notificationService.notifyError('Erro ao carregar, tente novamente')
      })
  }

  getRouteId() {
    return this.props.match.params.id
  }

  handleSaveClick = (e) => {
    e.preventDefault()

    const { plan } = this.state

    const { id } = this.props.match.params
    if (id) {
      planService.update(id, plan)
        .then(() => notificationService.notifySuccess('Salvo com sucesso'))
        .catch(() => notificationService.notifyError('Erro ao salvar, tente novamente'))
    } else {
      planService.create(plan)
        .then((data) => {
          notificationService.notifySuccess('Salvo com sucesso')
          this.setState({
            plan: data,
            redirect: true,
          })
        })
        .catch(() => notificationService.notifyError('Erro ao salvar, tente novamente'))
    }
  }

  handlePlanChange = mutation => (
    this.setState({
      plan: {
        ...this.state.plan,
        ...mutation,
      },
    })
  )

  handleChangeCustomer = (e, key, value) => this.handlePlanChange({
    customer: value,
  })

  handleAddActivity = () => {
    const lastestAppointment = last(sortByDate(this.state.plan.appointments, 'start'))
    const start = lastestAppointment ? moment(lastestAppointment.start).add(1, 'days').format('YYYY-MM-DDTHH:mm:ss') : moment().format('YYYY-MM-DDTHH:mm:ss')

    this.setState({
      plan: {
        ...this.state.plan,
        appointments: [...this.state.plan.appointments, { start }],
      },
    })
  }

  handleAppointmentChange = (appointment, mutation) => {
    this.setState(prevState => ({
      plan: {
        ...prevState.plan,
        appointments: prevState.plan.appointments.map(a => (appointment === a ? ({
          ...a,
          ...mutation,
        }) : a)),
      },
    }))
  }

  handleChangeAppointmentStart = (appointment, value) => {
    const newValue = moment(value).format('YYYY-MM-DDTHH:mm:ss')
    const appointmentAtSameTime = this.state.plan.appointments.find(a => a.start === newValue)
    if (appointmentAtSameTime) {
      notificationService.notifyError('Não podem haver duas aulas no mesmo horário')
      return
    }
    this.handleAppointmentChange(appointment, { start: newValue })
  }
  handleChangeAppointmentActivity = (appointment, e, key, value) => this.handleAppointmentChange(appointment, { activity: value, trainer: null })
  handleChangeAppointmentTrainer = (appointment, e, key, value) => this.handleAppointmentChange(appointment, { trainer: value })

  renderAppointmentForm(appointment, filteredActivities, i) {
    return (
      <div key={appointment.start}>
        <h5 style={globalStyles.sectionTitle}>Aula {i + 1}</h5>
        <div>
          <DateTimePicker
            clearIcon={null}
            value={appointment.start ? moment(appointment.start).format('DD/MM/YYYY HH:mm:ss') : null}
            format="DD/MM/YYYY HH:mm:ss"
            onChange={(...args) => this.handleChangeAppointmentStart(appointment, ...args)}
            DatePicker={DatePickerDialog}
            TimePicker={TimePickerDialog}
            floatingLabelText="Data e hora da atividade"
          />
        </div>

        <div>
          <SelectField
            hintText="Atividade"
            floatingLabelText="Atividade"
            value={appointment.activity}
            onChange={(...args) => this.handleChangeAppointmentActivity(appointment, ...args)}
          >
            {filteredActivities.map(activity => (<MenuItem
              key={activity.id}
              value={activity}
              primaryText={activity.name}
            />))}
          </SelectField>
        </div>

        {appointment.activity && (
          <div>
            <SelectField
              hintText="Treinador"
              floatingLabelText="Treinador"
              value={appointment.trainer}
              onChange={(...args) => this.handleChangeAppointmentTrainer(appointment, ...args)}
            >
              {appointment.activity.trainers.map(trainer => (<MenuItem
                key={trainer.id}
                value={trainer}
                primaryText={trainer.name}
              />))}
            </SelectField>
          </div>
        )}
        <Divider />
      </div>
    )
  }

  renderAppointmentList(plan, activities) {
    const sortedAppointments = sortByDate(plan.appointments, 'start')
    const filteredActivities = activities.filter(activity => plan.customer.restrictions.every(r1 => !activity.restrictions.find(r2 => r1.id === r2.id)))

    return (
      <div style={styles.appointmentList}>
        <h4 style={globalStyles.sectionTitle}>Aulas</h4>
        {sortedAppointments.map((appointment, i) => this.renderAppointmentForm(appointment, filteredActivities, i))}
        <FloatingActionButton
          style={styles.floatingActionButton}
          backgroundColor={pink500}
          onClick={this.handleAddActivity}
        >
          <ContentAdd />
        </FloatingActionButton>
      </div>
    )
  }

  renderPlanForm(plan, customers, activities) {
    return (
      <form>
        <div>
          <SelectField
            // não deixa editar se já foi salvo, ou se é o próprio cliente editando
            disabled={!!plan.id || loginService.isCustomer()}
            fullWidth
            hintText="Cliente do plano"
            floatingLabelText="Cliente do plano"
            value={plan.customer}
            onChange={this.handleChangeCustomer}
          >
            {customers.map(customer => (
              <MenuItem
                key={customer.name}
                insetChildren
                checked={plan.customer === customer}
                value={customer}
                primaryText={customer.name}
              />
            ))}
          </SelectField>
        </div>
        <div style={styles.buttons}>
          <RaisedButton
            onClick={this.handleSaveClick}
            label="Salvar"
            style={styles.saveButton}
            type="submit"
            primary
          />
        </div>
        {plan.customer && this.renderAppointmentList(plan, activities)}
      </form>
    )
  }

  render() {
    const {
      didLoad,
      redirect,
      plan,
      activities,
      customers,
    } = this.state

    if (redirect) {
      return (
        <Redirect to={`${PLAN_FORM}/${plan.id}`} />
      )
    }

    return (
      <PageBase
        title="Plano"
      >
        <Loader loaded={didLoad}>
          {this.renderPlanForm(plan, customers, activities)}
        </Loader>
      </PageBase>
    )
  }
}

PlanForm.propTypes = {
  match: PropTypes.shape({
    params: PropTypes.shape({
      id: PropTypes.string,
    }),
  }).isRequired,
}

export default PlanForm

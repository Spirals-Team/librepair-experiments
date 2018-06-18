import React, { Component } from 'react'
import { Link } from 'react-router-dom'
import { Table, TableBody, TableHeader, TableHeaderColumn, TableRow, TableRowColumn } from 'material-ui/Table'
import FloatingActionButton from 'material-ui/FloatingActionButton'
import ContentCreate from 'material-ui/svg-icons/content/create'
import ContentAdd from 'material-ui/svg-icons/content/add'
import { pink500, grey200, grey500 } from 'material-ui/styles/colors'

import { format, sortByDate } from '../../../../../utils/date'

import PageBase from '../../common/PageBase'
import planService from '../../../../../services/planService'

import { PLAN_FORM } from '../../../../../config/routes'

const styles = {
  floatingActionButton: {
    margin: 0,
    top: 'auto',
    right: 20,
    bottom: 20,
    left: 'auto',
    position: 'fixed',
  },
  editButton: {
    fill: grey500,
  },
  columns: {
    id: {
      width: '10%',
    },
    name: {
      width: '30%',
    },
    appointments: {
      width: '50%',
    },
    edit: {
      width: '10%',
    },
  },
}

class PlanList extends Component {
  state = {
    plans: [],
  }

  componentDidMount() {
    planService.all()
      .then(data => this.setState({ plans: data }))
      .catch(err => console.error(err))
  }

  renderHeader() {
    return (
      <TableHeader adjustForCheckbox={false} displaySelectAll={false}>
        <TableRow>
          <TableHeaderColumn style={styles.columns.name}>Cliente</TableHeaderColumn>
          <TableHeaderColumn style={styles.columns.appointments}>Aulas</TableHeaderColumn>
          <TableHeaderColumn style={styles.columns.edit}>Editar</TableHeaderColumn>
        </TableRow>
      </TableHeader>
    )
  }

  renderAppointments(appointments) {
    const sortedAppointments = sortByDate(appointments, 'start')
    return (
      <div>
        {sortedAppointments.map((appointment, i) => (
          <div key={appointment.id}>
            {`Aula ${i + 1}:`} {format(appointment.start)} - {appointment.activity.name} (com {appointment.trainer.name})
          </div>
        ))}
      </div>
    )
  }

  renderBody() {
    const { plans } = this.state
    return (
      <TableBody displayRowCheckbox={false}>
        {plans.map((plan => (
          <TableRow key={plan.id}>
            <TableRowColumn style={styles.columns.name}>{plan.customer.name}</TableRowColumn>
            <TableRowColumn style={styles.columns.appointments}>{this.renderAppointments(plan.appointments)}</TableRowColumn>
            <TableRowColumn style={styles.columns.edit}>
              <Link
                className="button"
                to={`${PLAN_FORM}/${plan.id}`}
              >
                <FloatingActionButton
                  zDepth={0}
                  mini
                  backgroundColor={grey200}
                  iconStyle={styles.editButton}
                >
                  <ContentCreate />
                </FloatingActionButton>
              </Link>
            </TableRowColumn>
          </TableRow>
        )))
      }
      </TableBody>
    )
  }

  render() {
    return (
      <PageBase
        title="Planos"
      >
        <div>
          <Link to={PLAN_FORM} >
            <FloatingActionButton style={styles.floatingActionButton} backgroundColor={pink500}>
              <ContentAdd />
            </FloatingActionButton>
          </Link>

          <Table>
            {this.renderHeader()}
            {this.renderBody()}
          </Table>
        </div>
      </PageBase>
    )
  }
}

export default PlanList

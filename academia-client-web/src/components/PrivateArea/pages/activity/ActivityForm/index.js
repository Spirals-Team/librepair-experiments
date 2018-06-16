import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { Redirect } from 'react-router-dom'

import MenuItem from 'material-ui/MenuItem'
import RaisedButton from 'material-ui/RaisedButton'
import SelectField from 'material-ui/SelectField'
import TextField from 'material-ui/TextField'

import { grey400 } from 'material-ui/styles/colors'

import Loader from 'react-loader'

import PageBase from '../../common/PageBase'

import { ACTIVITY_FORM } from '../../../../../config/routes'
import activityService from '../../../../../services/activityService'
import accountService from '../../../../../services/accountService'
import notificationService from '../../../../../services/notificationService'
import restrictionService from '../../../../../services/restrictionService'

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
}

class ActivityForm extends Component {
  state = {
    didLoad: false,
    redirect: false,

    activity: {
      name: '',
      description: '',
      restrictions: [],
      trainers: [],
    },
    restrictions: [],
    trainers: [],
  }

  componentDidMount() {
    const id = this.getRouteId()

    const activityPromise = id ? activityService.get(id) : Promise.resolve(this.state.activity)
    const restrictionsPromise = restrictionService.all()
    const trainersPromise = accountService.getTrainers()

    Promise.all([
      activityPromise,
      restrictionsPromise,
      trainersPromise,
    ])
      .then((data) => {
        const [activity, restrictions, trainers] = data
        this.setState({
          activity: {
            ...activity,
            restrictions: activity.restrictions.map(i => restrictions.find(i2 => i2.id === i.id)), // substitui as instâncias pelas instâncias do `all`
            trainers: activity.trainers.map(i => trainers.find(i2 => i2.id === i.id)), // substitui as instâncias pelas instâncias do `all`
          },
          restrictions,
          trainers,
          didLoad: true,
        })
      })
      .catch(() => {
        notificationService.notifyError('Erro ao carregar, tente novamente')
      })
  }

  getRouteId() {
    return this.props.match.params.id
  }

  handleSaveClick = (e) => {
    e.preventDefault()

    const { activity } = this.state

    const { id } = this.props.match.params
    if (id) {
      activityService.update(id, activity)
        .then(() => notificationService.notifySuccess('Salvo com sucesso'))
        .catch(() => notificationService.notifyError('Erro ao salvar, tente novamente'))
    } else {
      activityService.create(activity)
        .then((data) => {
          notificationService.notifySuccess('Salvo com sucesso')
          this.setState({
            activity: data,
            redirect: true,
          })
        })
        .catch(() => notificationService.notifyError('Erro ao salvar, tente novamente'))
    }
  }

  handleInputChange = (property, value) => (
    this.setState({
      activity: {
        ...this.state.activity,
        [property]: value,
      },
    })
  )

  handleNameChange = (e, value) => this.handleInputChange('name', value)
  handleDescriptionChange = (e, value) => this.handleInputChange('description', value)
  handleChangeRestrictions = (e, key, value) => this.handleInputChange('restrictions', value)
  handleChangeTrainers = (e, key, value) => this.handleInputChange('trainers', value)

  renderActivityForm(id, activity, restrictions, trainers) {
    const isRestrictionChecked = (activityRestrictions, restriction) => !!activityRestrictions.find(r => r.id === restriction.id)
    const isTrainerChecked = (activityTrainers, trainer) => !!activityTrainers.find(r => r.id === trainer.id)

    return (
      <form>
        <div>
          <TextField
            hintText="Nome"
            floatingLabelText="Nome"
            onChange={this.handleNameChange}
            value={activity.name}
          />
        </div>

        <div>
          <TextField
            fullWidth
            hintText="Description"
            floatingLabelText="Description"
            onChange={this.handleDescriptionChange}
            value={activity.description}
          />
        </div>

        <div>
          <SelectField
            multiple
            fullWidth
            hintText="Restrições associadas à atividade"
            floatingLabelText="Restrições associadas à atividade"
            value={activity.restrictions}
            onChange={this.handleChangeRestrictions}
          >
            {restrictions.map(r => (
              <MenuItem
                key={r.name}
                insetChildren
                checked={isRestrictionChecked(activity.restrictions, r)}
                value={r}
                primaryText={r.name}
              />
            ))}
          </SelectField>
        </div>

        <div>
          <SelectField
            multiple
            fullWidth
            hintText="Treinadores da atividade"
            floatingLabelText="Treinadores da atividade"
            value={activity.trainers}
            onChange={this.handleChangeTrainers}
          >
            {trainers.map(t => (
              <MenuItem
                key={t.name}
                insetChildren
                checked={isTrainerChecked(activity.trainers, t)}
                value={t}
                primaryText={t.name}
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
      </form>
    )
  }

  render() {
    const {
      activity,
      didLoad,
      redirect,
      restrictions,
      trainers,
    } = this.state

    if (redirect) {
      return (
        <Redirect to={`${ACTIVITY_FORM}/${activity.id}`} />
      )
    }

    const id = this.getRouteId()
    return (
      <PageBase
        title="Atividade"
      >
        <Loader loaded={didLoad}>
          {this.renderActivityForm(id, activity, restrictions, trainers)}
        </Loader>
      </PageBase>
    )
  }
}

ActivityForm.propTypes = {
  match: PropTypes.shape({
    params: PropTypes.shape({
      id: PropTypes.string,
    }),
  }).isRequired,
}

export default ActivityForm

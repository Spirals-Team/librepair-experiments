import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { Redirect } from 'react-router-dom'

import RaisedButton from 'material-ui/RaisedButton'
import TextField from 'material-ui/TextField'
import { grey400 } from 'material-ui/styles/colors'

import Loader from 'react-loader'

import PageBase from '../../common/PageBase'

import restrictionService from '../../../../../services/restrictionService'
import notificationService from '../../../../../services/notificationService'
import { RESTRICTION_FORM } from '../../../../../config/routes'

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
}

class RestrictionForm extends Component {
  state = {
    didLoad: false,
    redirect: false,

    restriction: {
      name: '',
    },
  }

  componentDidMount() {
    const id = this.getRouteId()

    const restrictionPromise = id ? restrictionService.get(id) : Promise.resolve(this.state.restriction)
    restrictionPromise
      .then((data) => {
        this.setState({
          restriction: data,
          didLoad: true,
        })
      })
  }

  getRouteId() {
    return this.props.match.params.id
  }

  handleSaveClick = (e) => {
    e.preventDefault()

    const { restriction } = this.state

    const { id } = this.props.match.params
    if (id) {
      restrictionService.update(id, restriction)
        .then(() => notificationService.notifySuccess('Salvo com sucesso'))
        .catch(() => notificationService.notifyError('Erro ao salvar, tente novamente'))
    } else {
      restrictionService.create(restriction)
        .then((data) => {
          notificationService.notifySuccess('Salvo com sucesso')
          this.setState({
            restriction: data,
            redirect: true,
          })
        })
        .catch(() => notificationService.notifyError('Erro ao salvar, tente novamente'))
    }
  }

  handleNameChange = (e, value) => {
    this.setState({
      restriction: {
        ...this.state.restriction,
        name: value,
      },
    })
  }

  renderForm(restriction) {
    return (
      <form>
        <TextField
          hintText="Nome"
          floatingLabelText="Nome"
          fullWidth
          onChange={this.handleNameChange}
          value={restriction.name}
        />
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
      didLoad,
      redirect,
      restriction,
    } = this.state

    if (redirect) {
      return (
        <Redirect to={`${RESTRICTION_FORM}/${restriction.id}`} />
      )
    }

    return (
      <PageBase
        title="Restrição"
      >
        <Loader loaded={didLoad}>
          {this.renderForm(restriction)}
        </Loader>
      </PageBase>
    )
  }
}

RestrictionForm.propTypes = {
  match: PropTypes.shape({
    params: PropTypes.shape({
      id: PropTypes.string,
    }),
  }).isRequired,
}

export default RestrictionForm

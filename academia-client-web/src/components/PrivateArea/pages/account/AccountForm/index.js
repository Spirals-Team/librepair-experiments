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

import { ACCOUNT_FORM } from '../../../../../config/routes'
import globalStyles from '../../../../../styles'
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

class AccountForm extends Component {
  state = {
    didLoad: false,
    redirect: false,

    settings: {
      accountType: [],
    },
    account: {
      name: '',
      username: '',
      password: '',
      type: null,
      restrictions: [],
    },
    passwordUpdateDto: {
      password: '',
      passwordConfirmation: '',
    },
    restrictions: [],
  }

  componentDidMount() {
    const id = this.getRouteId()

    const settingsPromise = accountService.settings()
    const accountPromise = id ? accountService.get(id) : Promise.resolve(this.state.account)
    const restrictionsPromise = restrictionService.all()

    Promise.all([
      settingsPromise,
      accountPromise,
      restrictionsPromise,
    ])
      .then((data) => {
        const [settings, account, restrictions] = data
        this.setState({
          settings,
          account: {
            ...account,
            type: id ? account.type : settings.accountType[0],
            restrictions: account.restrictions.map(r => restrictions.find(r2 => r2.id === r.id)), // substitui as instâncias pelas instâncias do `all`
          },
          restrictions,
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

  /*
    -------------
    account form
    -------------
  */
  handleSaveAccountClick = (e) => {
    e.preventDefault()

    const { account } = this.state

    const id = this.getRouteId()
    if (id) {
      accountService.update(id, account)
        .then(() => notificationService.notifySuccess('Salvo com sucesso'))
        .catch(() => notificationService.notifyError('Erro ao salvar, tente novamente'))
    } else {
      accountService.create(account)
        .then((data) => {
          notificationService.notifySuccess('Salvo com sucesso')
          this.setState({
            account: data,
            redirect: true,
          })
        })
        .catch(() => notificationService.notifyError('Erro ao salvar, tente novamente'))
    }
  }

  handleAccountInput = (property, value) => this.setState({
    account: {
      ...this.state.account,
      [property]: value,
    },
  })
  handleNameChange = (e, value) => this.handleAccountInput('name', value)
  handleUsernameChange = (e, value) => this.handleAccountInput('username', value)
  handlePasswordChange = (e, value) => this.handleAccountInput('password', value)
  handleAccountTypeChange = (e, key, value) => this.handleAccountInput('type', value)
  handleChangeRestrictions = (e, key, value) => this.handleAccountInput('restrictions', value)

  /*
    -------------
    password form
    -------------
  */
  handleSavePasswordClick = (e) => {
    e.preventDefault()

    const { passwordUpdateDto } = this.state

    const id = this.getRouteId()
    accountService.updatePassword(id, passwordUpdateDto)
      .then(() => notificationService.notifySuccess('Senha alterada com sucesso'))
      .catch(() => notificationService.notifyError('Erro ao salvar, tente novamente'))
  }

  handlePasswordUpdateInput = (property, value) => this.setState({
    passwordUpdateDto: {
      ...this.state.passwordUpdateDto,
      [property]: value,
    },
  })
  handlePasswordUpdateChange = (e, value) => this.handlePasswordUpdateInput('password', value)
  handlePasswordConfirmationUpdateChange = (e, value) => this.handlePasswordUpdateInput('passwordConfirmation', value)

  renderAccountForm(id, account, settings, restrictions) {
    const isRestrictionChecked = (accountRestrictions, restriction) => !!accountRestrictions.find(r => r.id === restriction.id)

    return (
      <form>
        <h4 style={globalStyles.sectionTitle}>Usuário</h4>
        <TextField
          hintText="Nome"
          floatingLabelText="Nome"
          fullWidth
          onChange={this.handleNameChange}
          value={account.name}
        />

        <div>
          <TextField
            disabled={!!id}
            hintText="Nome de usuário"
            floatingLabelText="Nome de usuário"
            onChange={this.handleUsernameChange}
            value={account.username}
          />
        </div>

        {!id && (
          <div>
            <TextField
              hintText="Senha"
              floatingLabelText="Senha"
              type="password"
              onChange={this.handlePasswordChange}
              value={account.password}
            />
          </div>
        )}

        <div>
          <SelectField
            hintText="Tipo de conta"
            floatingLabelText="Tipo de conta"
            value={account.type}
            onChange={this.handleAccountTypeChange}
          >
            {settings.accountType.map(t => (<MenuItem
              key={t}
              value={t}
              primaryText={accountService.translateAccountType(t)}
            />))}
          </SelectField>
        </div>

        <div>
          <SelectField
            multiple
            fullWidth
            hintText="Restrições associadas ao usuário"
            floatingLabelText="Restrições associadas ao usuário"
            value={account.restrictions}
            onChange={this.handleChangeRestrictions}
          >
            {restrictions.map(r => (
              <MenuItem
                key={r.name}
                insetChildren
                checked={isRestrictionChecked(account.restrictions, r)}
                value={r}
                primaryText={r.name}
              />
            ))}
          </SelectField>
        </div>

        <div style={styles.buttons}>
          <RaisedButton
            onClick={this.handleSaveAccountClick}
            label="Salvar usuário"
            style={styles.saveButton}
            type="submit"
            primary
          />
        </div>
      </form>
    )
  }

  renderPasswordForm(id, passwordUpdateDto) {
    if (!id) {
      return null
    }

    return (
      <form>
        <h4 style={globalStyles.sectionTitle}>Senha</h4>
        <div>
          <TextField
            hintText="Senha"
            floatingLabelText="Senha"
            type="password"
            onChange={this.handlePasswordUpdateChange}
            value={passwordUpdateDto.password}
          />
        </div>

        <div>
          <TextField
            hintText="Confirmação de Senha"
            floatingLabelText="Confirmação de Senha"
            type="password"
            onChange={this.handlePasswordConfirmationUpdateChange}
            value={passwordUpdateDto.passwordConfirmation}
          />
        </div>

        <div style={styles.buttons}>
          <RaisedButton
            onClick={this.handleSavePasswordClick}
            label="Salvar senha"
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
      account,
      didLoad,
      passwordUpdateDto,
      redirect,
      restrictions,
      settings,
    } = this.state

    if (redirect) {
      return (
        <Redirect to={`${ACCOUNT_FORM}/${account.id}`} />
      )
    }

    const id = this.getRouteId()
    return (
      <PageBase
        title="Usuário"
      >
        <Loader loaded={didLoad}>
          {this.renderAccountForm(id, account, settings, restrictions)}
          {this.renderPasswordForm(id, passwordUpdateDto)}
        </Loader>
      </PageBase>
    )
  }
}

AccountForm.propTypes = {
  match: PropTypes.shape({
    params: PropTypes.shape({
      id: PropTypes.string,
    }),
  }).isRequired,
}

export default AccountForm

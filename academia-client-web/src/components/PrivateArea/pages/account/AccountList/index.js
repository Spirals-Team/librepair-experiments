import React, { Component } from 'react'
import { Link } from 'react-router-dom'
import { Table, TableBody, TableHeader, TableHeaderColumn, TableRow, TableRowColumn } from 'material-ui/Table'
import FloatingActionButton from 'material-ui/FloatingActionButton'
import ContentCreate from 'material-ui/svg-icons/content/create'
import ContentAdd from 'material-ui/svg-icons/content/add'
import { pink500, grey200, grey500 } from 'material-ui/styles/colors'

import PageBase from '../../common/PageBase'
import accountService from '../../../../../services/accountService'

import { ACCOUNT_FORM } from '../../../../../config/routes'

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
      width: '40%',
    },
    username: {
      width: '20%',
    },
    type: {
      width: '20%',
    },
    edit: {
      width: '10%',
    },
  },
}

class AccountList extends Component {
  state = {
    accounts: [],
  }

  componentDidMount() {
    accountService.all()
      .then(data => this.setState({ accounts: data }))
      .catch(err => console.error(err))
  }

  renderHeader() {
    return (
      <TableHeader adjustForCheckbox={false} displaySelectAll={false}>
        <TableRow>
          <TableHeaderColumn style={styles.columns.name}>Nome</TableHeaderColumn>
          <TableHeaderColumn style={styles.columns.username}>Nome de usuário</TableHeaderColumn>
          <TableHeaderColumn style={styles.columns.type}>Tipo</TableHeaderColumn>
          <TableHeaderColumn style={styles.columns.edit}>Editar</TableHeaderColumn>
        </TableRow>
      </TableHeader>
    )
  }

  renderBody() {
    const { accounts } = this.state

    return (
      <TableBody displayRowCheckbox={false}>
        {accounts.map(item => (
          <TableRow key={item.id}>
            <TableRowColumn style={styles.columns.name}>{item.name}</TableRowColumn>
            <TableRowColumn style={styles.columns.username}>{item.username}</TableRowColumn>
            <TableRowColumn style={styles.columns.type}>{accountService.translateAccountType(item.type)}</TableRowColumn>
            <TableRowColumn style={styles.columns.edit}>
              <Link
                className="button"
                to={`${ACCOUNT_FORM}/${item.id}`}
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
        ))}
      </TableBody>
    )
  }

  render() {
    return (
      <PageBase
        title="Usuários"
      >
        <div>
          <Link to={ACCOUNT_FORM} >
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

export default AccountList

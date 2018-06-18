import React, { Component } from 'react'
import { Link } from 'react-router-dom'
import { Table, TableBody, TableHeader, TableHeaderColumn, TableRow, TableRowColumn } from 'material-ui/Table'
import FloatingActionButton from 'material-ui/FloatingActionButton'
import ContentCreate from 'material-ui/svg-icons/content/create'
import ContentAdd from 'material-ui/svg-icons/content/add'
import { pink500, grey200, grey500 } from 'material-ui/styles/colors'

import PageBase from '../../common/PageBase'
import activityService from '../../../../../services/activityService'

import { ACTIVITY_FORM } from '../../../../../config/routes'

const styles = {
  floatingActionButton: {
    margin: 0,
    top: 'auto',
    right: 20,
    bottom: 20,
    left: 'auto',
    position: 'fixed',
  },
  detailButton: {
    fill: grey500,
  },
  editButton: {
    fill: grey500,
  },
  columns: {
    name: {
      width: '40%',
    },
    description: {
      width: '50%',
    },
    edit: {
      width: '10%',
    },
  },
}

class ActivityList extends Component {
  state = {
    activities: [],
  }

  componentDidMount() {
    activityService.all()
      .then(data => this.setState({ activities: data }))
      .catch(err => console.error(err))
  }

  renderHeader() {
    return (
      <TableHeader adjustForCheckbox={false} displaySelectAll={false}>
        <TableRow>
          <TableHeaderColumn style={styles.columns.name}>Nome</TableHeaderColumn>
          <TableHeaderColumn style={styles.columns.description}>Descrição</TableHeaderColumn>
          <TableHeaderColumn style={styles.columns.edit}>Editar</TableHeaderColumn>
        </TableRow>
      </TableHeader>
    )
  }

  renderBody() {
    const { activities } = this.state
    return (
      <TableBody displayRowCheckbox={false}>
        {activities.map(item => (
          <TableRow key={item.id}>
            <TableRowColumn style={styles.columns.name}>{item.name}</TableRowColumn>
            <TableRowColumn style={styles.columns.description}>{item.description}</TableRowColumn>
            <TableRowColumn style={styles.columns.edit}>
              <Link
                className="button"
                to={`${ACTIVITY_FORM}/${item.id}`}
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
        title="Atividades"
      >
        <div>
          <Link to={ACTIVITY_FORM} >
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

export default ActivityList

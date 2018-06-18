import React, { Component } from 'react'
import PropTypes from 'prop-types'

import Avatar from 'material-ui/Avatar'
import { List, ListItem } from 'material-ui/List'
import Subheader from 'material-ui/Subheader'
import Divider from 'material-ui/Divider'
import Paper from 'material-ui/Paper'
import DirectionsRun from 'material-ui/svg-icons/maps/directions-run'
import NewReleases from 'material-ui/svg-icons/av/new-releases'
import { cyan600, pink600, purple600, orange600, grey200, white } from 'material-ui/styles/colors'
import { typography } from 'material-ui/styles'

import Today from 'material-ui/svg-icons/action/today'
import Assignment from 'material-ui/svg-icons/action/assignment'
import Face from 'material-ui/svg-icons/action/face'
import Star from 'material-ui/svg-icons/toggle/star'

import InfoBox from '../common/InfoBox'

import { format, isToday, sortByDate } from '../../../../../utils/date'

const styles = {
  subheader: {
    fontSize: 24,
    fontWeight: typography.fontWeightLight,
    backgroundColor: cyan600,
    color: white,
  },
  today: {
    backgroundColor: grey200,
  },
}

export default class DashboardAdmin extends Component {
  static propTypes = {
    data: PropTypes.shape({
      customers: PropTypes.array.isRequired,
      trainers: PropTypes.array.isRequired,
      plans: PropTypes.array.isRequired,
      trainerAppointments: PropTypes.array.isRequired,
    }).isRequired,
  }

  renderTodayTrainerAppointments(trainerAppointments) {
    const sortedTrainerAppointments = sortByDate(trainerAppointments, 'appointment.start')

    return (
      <Paper>
        <List>
          <Subheader style={styles.subheader}>Aulas hoje</Subheader>
          {sortedTrainerAppointments.map(({ appointment, customer }) => (
            <div key={appointment.id} style={isToday(appointment.start) ? styles.today : {}}>
              <ListItem
                leftAvatar={<Avatar icon={<DirectionsRun />} />}
                primaryText={`${format(appointment.start)}: ${appointment.activity.name} (de ${customer.name} com ${appointment.trainer.name})`}
                secondaryText={appointment.activity.description}
                rightIcon={isToday(appointment.start) ? <NewReleases /> : null}
              />
              <Divider inset />
            </div>
          ))}
        </List>
      </Paper>
    )
  }

  render() {
    const { data } = this.props
    const {
      customers,
      trainers,
      plans,
      trainerAppointments,
    } = data

    const appointmentsToday = trainerAppointments.filter(({ appointment }) => isToday(appointment.start))

    return (
      <div>
        <div className="row">
          <div className="col-xs-12 col-sm-6 col-md-3 col-lg-3 m-b-15">
            <InfoBox
              Icon={Today}
              color={pink600}
              title="Aulas hoje"
              value={appointmentsToday.length}
            />
          </div>
          <div className="col-xs-12 col-sm-6 col-md-3 col-lg-3 m-b-15">
            <InfoBox
              Icon={Assignment}
              color={orange600}
              title="Planos ativos"
              value={plans.length}
            />
          </div>
          <div className="col-xs-12 col-sm-6 col-md-3 col-lg-3 m-b-15">
            <InfoBox
              Icon={Face}
              color={cyan600}
              title="Alunos"
              value={customers.length}
            />
          </div>
          <div className="col-xs-12 col-sm-6 col-md-3 col-lg-3 m-b-15">
            <InfoBox
              Icon={Star}
              color={purple600}
              title="Professores"
              value={trainers.length}
            />
          </div>
        </div>
        <div className="row">
          <div className="col-xs-12 col-sm-12 col-md-12 col-lg-12 m-b-15">
            {this.renderTodayTrainerAppointments(appointmentsToday)}
          </div>
        </div>
      </div>
    )
  }
}

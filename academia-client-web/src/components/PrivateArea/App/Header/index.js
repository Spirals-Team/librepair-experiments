import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { Redirect } from 'react-router-dom'
import AppBar from 'material-ui/AppBar'
import IconButton from 'material-ui/IconButton'
import IconMenu from 'material-ui/IconMenu'
import MenuItem from 'material-ui/MenuItem'
import MoreVertIcon from 'material-ui/svg-icons/navigation/more-vert'
import Menu from 'material-ui/svg-icons/navigation/menu'
import { white } from 'material-ui/styles/colors'

import loginService from '../../../../services/loginService'

import { LOGIN } from '../../../../config/routes'

class Header extends Component {
  state = {
    logout: false,
  }

  handleLogoutClick = () => {
    loginService.logout()
      .then(() => {
        this.setState({ logout: true })
      })
      .catch(err => console.log(err))
  }

  render() {
    const { styles, handleChangeRequestNavDrawer } = this.props

    const style = {
      appBar: {
        position: 'fixed',
        top: 0,
        overflow: 'hidden',
        maxHeight: 57,
      },
      menuButton: {
        marginLeft: 10,
      },
      iconsRightContainer: {
        marginLeft: 20,
      },
    }

    const { logout } = this.state

    if (logout) {
      return (
        <Redirect to={LOGIN} />
      )
    }

    return (
      <div>
        <AppBar
          style={{ ...styles, ...style.appBar }}
          iconElementLeft={
            <IconButton style={style.menuButton} onClick={handleChangeRequestNavDrawer}>
              <Menu color={white} />
            </IconButton>
              }
          iconElementRight={
            <div style={style.iconsRightContainer}>
              <IconMenu
                color={white}
                iconButtonElement={
                  <IconButton><MoreVertIcon color={white} /></IconButton>
                    }
                targetOrigin={{ horizontal: 'right', vertical: 'top' }}
                anchorOrigin={{ horizontal: 'right', vertical: 'top' }}
              >
                <MenuItem
                  primaryText="Sair"
                  onClick={this.handleLogoutClick}
                />
              </IconMenu>
            </div>
              }
        />
      </div>
    )
  }
}

Header.propTypes = {
  styles: PropTypes.object, // eslint-disable-line react/forbid-prop-types
  handleChangeRequestNavDrawer: PropTypes.func.isRequired,
}

Header.defaultProps = {
  styles: {},
}

export default Header

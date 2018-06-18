import React from 'react'
import PropTypes from 'prop-types'
import Paper from 'material-ui/Paper'
import Divider from 'material-ui/Divider'
import globalStyles from '../../../../styles'

const PageBase = (props) => {
  const { title } = props

  return (
    <div className="PageBase">
      <Paper style={globalStyles.paper}>
        <h3 style={globalStyles.title}>{title}</h3>

        <Divider />
        {props.children}

        <div style={globalStyles.clear} />

      </Paper>
    </div>
  )
}

PageBase.propTypes = {
  title: PropTypes.string.isRequired,
  children: PropTypes.element,
}

PageBase.defaultProps = {
  children: null,
}

export default PageBase

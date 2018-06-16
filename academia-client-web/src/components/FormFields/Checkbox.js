cont Checkbox = {

  const { checkboxes } = this.props
  const listCheckboxes = checkboxes.map((item, i) => (
    <label id={item.id} key={i}>
      <input 
        type="checkbox"
        value={item.value}
        checked={isChecked}
      />
    </label>
	))

  return (
  <div>
    { listCheckboxes }
  </div>
  )
}

Checkbox.propTypes = {
  checkboxes: PropTypes.node.isRequired,
}

export default Checkbox

import moment from 'moment'
import get from 'lodash/get'
import sortBy from 'lodash/sortBy'

const format = date => moment(date).format('DD/MM/YYYY HH:mm:ss')

const sortByDate = (list, property) => sortBy(list, item => moment(get(item, property)).valueOf())

const isToday = date => moment().isSame(moment(date), 'd')

export {
  format,
  isToday,
  sortByDate,
}

import apiService from './apiService'
import accountTypes from '../constants/accountTypes'

const PREFIX = '/api/private/accounts'

class AccountService {
  all() {
    return apiService.get(PREFIX)
      .then(({ data }) => data)
  }

  get(id) {
    return apiService.get(`${PREFIX}/${id}`)
      .then(({ data }) => data)
  }

  update(id, updateData) {
    return apiService.put(`${PREFIX}/${id}`, updateData)
      .then(({ data }) => data)
  }

  create(createData) {
    return apiService.post(PREFIX, createData)
      .then(({ data }) => data)
  }

  updatePassword(id, updateData) {
    return apiService.put(`${PREFIX}/password/${id}`, updateData)
      .then(({ data }) => data)
  }

  settings() {
    return apiService.get(`${PREFIX}/settings`)
      .then(({ data }) => data)
  }

  translateAccountType(accountType) {
    const map = {
      [accountTypes.ADMIN]: 'Administrador',
      [accountTypes.CUSTOMER]: 'Cliente',
      [accountTypes.TRAINER]: 'Treinador',
    }

    if (map[accountType]) {
      return map[accountType]
    }
    return '-'
  }

  getTrainers() {
    return apiService.get(`${PREFIX}/find/trainers`)
      .then(({ data }) => data)
  }

  getCustomers() {
    return apiService.get(`${PREFIX}/find/customers`)
      .then(({ data }) => data)
  }
}

export default new AccountService()

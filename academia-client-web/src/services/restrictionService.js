import apiService from './apiService'

const PREFIX = '/api/private/restrictions'

class RestrictionService {
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
}

export default new RestrictionService()

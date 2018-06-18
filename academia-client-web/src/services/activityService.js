import apiService from './apiService'

class ActivityService {
  all() {
    return apiService.get('/api/private/activities')
      .then(({ data }) => data)
  }

  get(id) {
    return apiService.get(`/api/private/activities/${id}`)
      .then(({ data }) => data)
  }

  update(id, updateData) {
    return apiService.put(`/api/private/activities/${id}`, updateData)
      .then(({ data }) => data)
  }

  create(createData) {
    return apiService.post('/api/private/activities', createData)
      .then(({ data }) => data)
  }
}

export default new ActivityService()

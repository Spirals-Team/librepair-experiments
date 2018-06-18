import apiService from './apiService'

const PREFIX = '/api/private/dashboard'

class DashboardService {
  all() {
    return apiService.get(PREFIX)
      .then(({ data }) => data)
  }
}

export default new DashboardService()

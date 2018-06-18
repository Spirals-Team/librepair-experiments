import { toast } from 'react-toastify'

class NotificationService {
  notifyError(message) {
    return toast.error(message)
  }

  notifySuccess(message) {
    return toast.success(message)
  }

  dismissAll() {
    toast.dismiss()
  }
}

export default new NotificationService()

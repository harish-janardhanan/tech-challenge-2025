import { makeAutoObservable } from 'mobx';

class UserStore {
  user = null;
  isLoading = false;
  error = null;
  authenticated = false;

  constructor() {
    makeAutoObservable(this);
  }

  setUser(user) {
    this.user = user;
    this.authenticated = !!user;
  }

  setLoading(isLoading) {
    this.isLoading = isLoading;
  }

  setError(error) {
    this.error = error;
  }

  logout() {
    this.user = null;
    this.authenticated = false;
  }
}

const userStore = new UserStore();
export default userStore;

const key = 'user'

let initial = null;
try {
  localStorage.setItem(key, JSON.stringify({
    token: "this_is_token",
    uid: "this_is_uid",
    isAdmin: true
  }))
  initial = JSON.parse(localStorage.getItem(key));
} catch (e) {
  console.log("Unable to get session data: " + String(e))
}



const user = {
  namespaced: true,
  state() {
    return {
      user: initial
    }
  },
  getters: {
    getUser: function (state) {
      if (!state.user) {
        state.user = JSON.parse(localStorage.getItem(key))
      }
      return state.user
    },
  },
  mutations: {
    $_setStorage(state, value) {
      state.user = value
      localStorage.setItem(key, JSON.stringify(value))
    },
    $_removeStorage(state) {
      state.user = null
      localStorage.removeItem(key)
    },
  },
  actions: {
    saveUserInfo({commit}, data) {
      commit('$_setStorage', data)
    },
    clearUserInfo({commit}) {
      commit('$_removeStorage')
    }
  }
}

export default user

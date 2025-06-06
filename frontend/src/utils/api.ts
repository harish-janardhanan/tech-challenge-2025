import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Example: GET /trades
export const fetchTrades = () => api.get('/trades');
// Example: POST /trades
export const createTrade = (trade) => api.post('/trades', trade);

// Example: GET /trades/:id
export const fetchTradeById = (id) => api.get(`/trades/${id}`);

// Example: PUT /trades/:id
export const updateTrade = (id, trade) => api.put(`/trades/${id}`, trade);

// Example: DELETE /trades/:id
export const deleteTrade = (id) => api.delete(`/trades/${id}`);

export const fetchAllUsers = async () => {
  console.log("Fetching all users from the API");
  return await api.get('/users').then((res) => {return res});
};
// POST /users
export const createUser = (user) => api.post('/users', user);
// PUT /users/:id
export const updateUser = (id, user) => api.put(`/users/${id}`, user);
// Add more API methods as needed for other entities
export const authenticate = (user: string, pass: string) => {
  return api.post(`/login/${user}`, null, {
    params: {
      Authorization: pass
    }
  });
}

export const getUserByLogin = (login: string) => {
    return api.get(`/users/loginId/${login}`);
}
export default api;

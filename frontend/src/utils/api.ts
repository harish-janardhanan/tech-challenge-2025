import axios from 'axios';

const api = axios.create({
  baseURL: '/api', // Adjust baseURL as needed
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

// Add more API methods as needed for other entities

export default api;


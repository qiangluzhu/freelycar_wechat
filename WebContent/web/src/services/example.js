import request from '../utils/request';

export default {
  query: () => {
    return request('/api/users');
  },
  add: (option) => {
    return request('/api/car/listbrand',option)
  }
} 

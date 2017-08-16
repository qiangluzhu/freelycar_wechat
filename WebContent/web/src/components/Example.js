import React from 'react';
import { add } from '../services/example.js'
import parseForm from '../utils/parseToForm.js'
const Example = () => {
  console.log(add({
    method: 'get', headers: {
      'Accept': 'application/json',
      // 'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
    }
  }))
  return (
    <div>
      Example
    </div>
  );
};

Example.propTypes = {
};

export default Example;

import React from 'react';

const URL = "http://localhost:8080/Backend/api/car";


function handleHttpErrors(res) {
  if (!res.ok) {
    throw { message: res.statusText, status: res.status };
  }
  return res.json();
}

class Facade {

  fetchData = () => {
    const options = this.makeFetchOptions("GET");
    return fetch(URL, options).then(handleHttpErrors);
  }

  fetchSpecCar = (regno) => {
    const options = this.makeFetchOptions("GET");
    return fetch(URL+"/regno/"+regno, options).then(handleHttpErrors);
  }

  makeFetchOptions = (type, b) => {
    let headers = {
      'Origin': '',
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    }

    return {
      method: type,
      headers,
      body: JSON.stringify(b)
    }
  }



}

const facade = new Facade();
export default facade;
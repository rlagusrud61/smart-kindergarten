import React, {useEffect} from 'react';
import logo from '../../img/logo.svg';
import '../../styles/App.css';
import {ClassroomClient} from "../../clients/rest/ApiClient";

function Home() {

  useEffect(() => {
    console.log("aaaaa")
    new ClassroomClient().classroom().then(data => {
      console.log("classrooms", data);
    })
  })

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/components/pages/Home.tsx</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

export default Home;

import React, { useEffect } from 'react';
import logo from '../../img/logo.svg';
import '../../styles/App.css';
import '../../styles/tailwind.css'
import { ClassroomClient } from "../../clients/rest/ApiClient";

function Home() {

  useEffect(() => {
    console.log("aaaaa")
    new ClassroomClient().classroom().then(data => {
      console.log("classrooms", data);
    })
  })

  return (
    <div className="grid grid-rows-3 grid-flow-col gap-4">
      <div className="row-span-3 ...">1</div>
      <div className="col-span-2 ...">2</div>
      <div className="row-span-2 col-span-2 ...">3</div>
    </div>
  );
}

export default Home;

import React, { useEffect } from 'react';
import logo from '../../img/logo.svg';
import '../../styles/tailwind.css'
import { ClassroomClient } from "../../clients/rest/ApiClient";
import { StudentProfile } from './../layout/StudentProfile';
import { StudentList } from './../layout/StudentList';
function Home() {

  useEffect(() => {
    console.log("aaaaa")
    new ClassroomClient().classroom().then(data => {
      console.log("classrooms", data);
    })
  })

  return (


    <main className="relative h-screen">



      
      <div className="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
        {/* <!-- Replace with your content --> */}
        <div className="grid grid-cols-3 gap-20 px-4 py-6 sm:px-0">
          <div className="...">

            <h1 className="py-10 text-center text-3xl font-bold text-gray-900">Students</h1>
            <div className="bg-white shadow-lg rounded-lg h-screen">
             <StudentList></StudentList> 
            </div>
          </div>
          <div className="col-span-2 ...">

            <h1 className="py-10 text-center text-3xl font-bold text-gray-900">Student Profile</h1>
            <div className="overflow-auto border-4 border-dashed border-gray-200 rounded-lg h-screen">
              <StudentProfile></StudentProfile>
            </div>

          </div>
        </div>
        {/* <!-- /End replace --> */}
      </div>

    </main>

  );
}

export default Home;

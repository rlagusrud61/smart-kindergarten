import { useEffect, useState } from 'react';
import logo from '../../img/logo.svg';
import '../../styles/tailwind.css'
import { ClassroomClient } from "../../clients/rest/ApiClient";
import { StudentProfile } from './../layout/StudentProfile';
import { StudentList } from './../layout/StudentList';

function Home() {

  const [selectedStudent, setSelectedStudent] = useState <number>();

    const students = [
        "Jan",
        "Michiel",
        "Jeroen",
        "Aachi",
        "Niels",
        "Hyunk",
        "X Æ A-12",
    ];

  useEffect(() => {
    console.log("aaaaa")
    new ClassroomClient().classroom().then(data => {
      console.log("classrooms", data);
    })
  })

  return (
    <main className="relative h-screen">
      <div className="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
        <div className="grid grid-cols-3 gap-20 px-4 py-6 sm:px-0">
          <div className="...">

            <h1 className="py-10 text-center text-4xl font-bold text-gray-700">Students</h1>
            <div className="bg-gray-100 shadow-lg rounded-lg h-screen">
             <StudentList students={students} setSelectedStudent={setSelectedStudent}/> 
            </div>
          </div>
          <div className="col-span-2 ...">

            <h1 className="py-10 text-center text-4xl font-bold text-gray-700">Student Profile</h1>
            <div className="overflow-auto bg-gray-100 shadow-lg rounded-lg h-screen">
              <StudentProfile student={students[selectedStudent ?? 0]}/>
            </div>

          </div>
        </div>
      </div>

    </main>

  );
}

export default Home;

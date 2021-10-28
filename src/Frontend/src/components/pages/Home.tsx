import { useEffect, useState } from 'react';
import logo from '../../img/logo.svg';
import '../../styles/tailwind.css'
import {ClassroomClient, Student, StudentsClient} from "../../clients/rest/ApiClient";
import {StudentProfile} from './../layout/StudentProfile';
import {StudentList} from './../layout/StudentList';

function Home() {

    const [selectedStudent, setSelectedStudent] = useState<Student>();
    const [students, setStudents] = useState<Student[]>([]);

    useEffect(() => {
        new StudentsClient().students().then((students) => {
            setStudents(students)
            setSelectedStudent(students[0])
        })
    }, [])

    return (
        <main className="relative h-screen">
            <div className="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
                <div className="grid grid-cols-3 gap-20 px-4 py-6 sm:px-0">
                    <div>
                        <h1 className="py-10 text-center text-4xl font-bold text-gray-700">Students</h1>
                        <div className="bg-gray-100 shadow-lg rounded-lg h-screen">
                            <StudentList students={students} setSelectedStudent={setSelectedStudent}/>
                        </div>
                    </div>
                    <div className="col-span-2 ...">

                        <h1 className="py-10 text-center text-4xl font-bold text-gray-700">Student Profile</h1>
                        <div className="overflow-auto bg-gray-100 shadow-lg rounded-lg h-screen">
                            {/*This is probably cursed*/}
                            {selectedStudent && (<StudentProfile student={selectedStudent}/>)}
                        </div>

          </div>
        </div>
      </div>

    </main>

  );
}

export default Home;

import {useEffect, useState} from 'react';
import '../../styles/tailwind.css'
import {Student, StudentsClient} from "../../clients/rest/ApiClient";
import {StudentProfile} from '../Student/StudentProfile';
import {StudentList} from '../Student/StudentList';

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
        <main className="relative h-screen overflow-y-hidden pt-16">
            <div className="max-w-7xl mx-auto sm:px-6 lg:px-8 h-full">
                <div className="grid grid-cols-3 grid-rows-2 gap-20 px-4 py-12 sm:px-0 h-full max-h-full overflow-y-hidden" style={{gridTemplateRows:"4rem 1fr",gridRowGap:0}}>
                    <h1 className="text-center text-4xl font-bold text-gray-700">Students</h1>
                    <h1 className="text-center text-4xl font-bold text-gray-700 col-span-2">Student Profile</h1>

                    <div className="flex flex-col h-full max-h-full">
                        <div className="bg-gray-100 shadow-lg rounded-lg flex-grow">
                            <StudentList students={students} setSelectedStudent={setSelectedStudent}/>
                        </div>
                    </div>
                    <div className="col-span-2 h-full max-h-full overflow-y-hidden bg-gray-100 shadow-lg rounded-lg p-7 flex flex-col gap-7">
                        {selectedStudent && (<StudentProfile student={selectedStudent}/>)}
                    </div>
                </div>
            </div>
        </main>
    );
}

export default Home;

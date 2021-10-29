import { SearchBar } from "../components/SearchBar";
import { useState } from "react";
import {Student} from "../../clients/rest/ApiClient";
import {ImagePathForStudent} from "./StudentProfile";

interface StudentListProps {
    students: Student[],
    setSelectedStudent: any,
}

export const StudentList = ({students, setSelectedStudent}:StudentListProps) => {
    const [searchQuery, setSearchQuery] = useState("");

    return (
        <div className="overflow-auto h-full px-7">
            <SearchBar searchQuery={searchQuery} setSearchQuery={setSearchQuery} />


            {/* List of Students */}
            <div className="bg-white shadow-lg rounded-xl">
                {students
                    .filter((student) => {
                        return student.name?.toLowerCase().includes(searchQuery.toLowerCase()) || searchQuery === "";
                    })
                    .map((student) => {
                        return (
                            <button onClick={() => {setSelectedStudent(student)}} key={student.id} className="p-3 grid grid-cols-3">
                                <img className="col-span-1 object-contain transform w-16 rounded-full" alt='kid' src={ImagePathForStudent(student)} />
                                <div className="flex justify-center align-middle col-span-2">
                                    <h1 className="align-middle text-center text-lg">{student.name}</h1>
                                </div>
                            </button>
                        );
                    })}
            </div>
        </div>
    );
}

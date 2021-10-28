import { SearchBar } from "./SearchBar";
import { useState } from "react";

interface StudentListProps {
    students: string[],
    setSelectedStudent: any,
}

export const StudentList = ({students, setSelectedStudent}:StudentListProps) => {
    const [searchQuery, setSearchQuery] = useState("");
    

    return (
        <div className="overflow-auto h-full px-5">
            <SearchBar searchQuery={searchQuery} setSearchQuery={setSearchQuery} />


            {/* List of Students */}
            <div className="bg-white shadow-lg rounded-xl">
                {students
                    .filter((student) => {
                        if (student.toLowerCase().includes(searchQuery.toLowerCase()) || searchQuery == "") {
                            return true;
                        } return false;
                    })
                    .map((student, id) => {
                        return (
                            <button onClick={() => {setSelectedStudent(id)}} key={id} className="p-3 grid grid-cols-3">
                                <img className="col-span-1 object-contain transform w-16 rounded-full" alt='kid' src="img/kidlmao.jpg" />
                                <div className="table align-middle row-span-2 col-span-2">
                                    <h1 className="table-cell align-middle text-justify text-lg">{student}</h1>
                                </div>
                            </button>
                        );
                    })}
            </div>
        </div>
    );
}
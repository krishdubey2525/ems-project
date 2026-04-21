import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { deleteEmployee, getDashboardStats, listEmployees } from "./Service/EmployeeService";
import StatCardComponent from "./StatCardComponent";
import toast from 'react-hot-toast'; // ✅ Step 1: Added import

const ListEmployeesComponent = () => {
    const [employees, setEmployees] = useState([]); 
    const [filteredEmployees, setFilteredEmployees] = useState([]);
    const [stats, setStats] = useState({ totalEmployees: 0, totalDepartments: 0 });

    const navigate = useNavigate();

    useEffect(() => {
        loadData();
    }, []);

    const loadData = () => {
        listEmployees().then((response) => {
            setEmployees(response.data);
            setFilteredEmployees(response.data); 
        }).catch(error => console.error(error));

        getDashboardStats().then((response) => {
            setStats(response.data);
        }).catch(error => console.error("Error fetching stats:", error));
    };

    const downloadExcel = () => {
        window.location.href = "http://localhost:8080/api/reports/excel";
    };

    const handleSearch = (e) => {
        const val = e.target.value.toLowerCase();
        const filtered = employees.filter(emp => 
            emp.firstname.toLowerCase().includes(val) || 
            emp.lastname.toLowerCase().includes(val)
        );
        setFilteredEmployees(filtered);
    };

    // ✅ Step 2: Updated removeEmployee with toast notifications
    function removeEmployee(id) {
        if (window.confirm("Are you sure you want to delete this employee?")) {
            deleteEmployee(id).then(() => {
                toast.success('Employee deleted successfully!', {
                    icon: '🗑️',
                });
                loadData(); 
            }).catch(error => {
                toast.error("Failed to delete employee.");
                console.error(error);
            });
        }
    }

    return (
        <div className="container pb-5">
            <h2 className='text-center my-4'>Employee Management Dashboard</h2>

            <div className="row mb-4">
                <div className="col-md-6">
                    <StatCardComponent title="Total Employees" value={stats.totalEmployees} color="primary" />
                </div>
                <div className="col-md-6">
                    <StatCardComponent title="Departments" value={stats.totalDepartments} color="success" />
                </div>
            </div>

            <div className="d-flex justify-content-between mb-3 align-items-center">
                <div>
                    <button className='btn btn-primary me-2 shadow-sm' onClick={() => navigate('/add-employee')}>
                        Add Employee
                    </button>
                    <button className='btn btn-success shadow-sm' onClick={downloadExcel}>
                        Download Excel
                    </button>
                </div>
                
                <input 
                    type="text" 
                    className="form-control w-25 shadow-sm" 
                    placeholder="Search by name..." 
                    onChange={handleSearch} 
                />
            </div>

            <div className="shadow-sm border rounded" style={{ maxHeight: '450px', overflowY: 'auto' }}>
                <table className='table table-hover table-bordered mb-0'>
                    <thead className="table-dark" style={{ position: 'sticky', top: 0, zIndex: 1 }}>
                        <tr>
                            <th>ID</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Email ID</th>
                            <th>Assigned Task</th>
                            <th>Deadline</th>
                            <th>Status</th> 
                            <th className="text-center">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            filteredEmployees.length > 0 ? (
                                filteredEmployees.map(employee =>
                                    <tr key={employee.id}>
                                        <td>{employee.id}</td>
                                        <td>{employee.firstname}</td>
                                        <td>{employee.lastname}</td>
                                        <td>{employee.email}</td>
                                        <td>{employee.assignedTask || <span className="text-muted">No task</span>}</td>
                                        <td>
                                            {employee.deadline ? (
                                                <span className="badge bg-light text-dark border">{employee.deadline}</span>
                                            ) : (
                                                <span className="text-muted">N/A</span>
                                            )}
                                        </td>
                                        
                                        <td>
                                            {employee.taskStatus === 'COMPLETED' ? (
                                                <span className="badge bg-success w-100">Completed</span>
                                            ) : employee.taskStatus === 'IN_PROGRESS' ? (
                                                <span className="badge bg-warning text-dark w-100">In Progress</span>
                                            ) : (
                                                <span className="badge bg-secondary w-100">Pending</span>
                                            )}
                                        </td>

                                        <td className="text-center">
                                            <button className='btn btn-info btn-sm me-2' onClick={() => navigate(`/edit-employee/${employee.id}`)}>Update</button>
                                            <button className='btn btn-danger btn-sm' onClick={() => removeEmployee(employee.id)}>Delete</button>
                                        </td>
                                    </tr>)
                            ) : (
                                <tr>
                                    <td colSpan="8" className="text-center py-4 text-muted">No employees found matching the criteria.</td>
                                </tr>
                            )
                        }
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default ListEmployeesComponent;
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { getAllDepartment } from "./Service/DepartmentService";
import { createEmployee, getEmployee, updateEmployee } from "./Service/EmployeeService";
import toast from 'react-hot-toast';

const EmployeeComponent = () => {
  const [firstname, setFirstName] = useState("");
  const [lastname, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [assignedTask, setAssignedTask] = useState('');
  const [deadline, setDeadline] = useState('');
  const [taskStatus, setTaskStatus] = useState('PENDING'); 
  const [departmentId, setDepartmentId] = useState('');
  const [departments, setDepartments] = useState([]);

  const { id } = useParams();
  const navigate = useNavigate();

  const [errors, setErrors] = useState({
    firstname: "",
    lastname: "",
    email: "",
    department: ""
  });

  // Fetch departments for the dropdown on component load
  useEffect(() => {
    getAllDepartment().then((response) => {
      setDepartments(response.data);
    }).catch(error => {
      console.error("Error fetching departments:", error);
    });
  }, []);

  // Load employee data if we are in "Update" mode
  useEffect(() => {
    if (id) {
      getEmployee(id)
        .then((response) => {
          setFirstName(response.data.firstname);
          setLastName(response.data.lastname);
          setEmail(response.data.email);
          setDepartmentId(response.data.departmentId);
          setAssignedTask(response.data.assignedTask || "");
          setDeadline(response.data.deadline || "");
          setTaskStatus(response.data.taskStatus || "PENDING"); 
        })
        .catch((error) => {
          console.error("Error loading employee:", error);
          toast.error("Could not load employee details.");
        });
    }
  }, [id]);

  function saveOrUpdateEmployee(e) {
    e.preventDefault();

    if (validateForm()) {
      const employee = { 
        firstname, 
        lastname, 
        email, 
        departmentId, 
        assignedTask, 
        deadline,
        taskStatus 
      };

      if (id) {
        updateEmployee(id, employee)
          .then(() => {
            toast.success("Employee updated successfully!");
            navigate("/employees");
          })
          .catch((error) => {
            toast.error("Failed to update employee.");
            console.error(error);
          });
      } else {
        createEmployee(employee)
          .then(() => {
            toast.success("Employee added successfully!");
            navigate("/employees");
          })
          .catch((error) => {
            toast.error("Failed to add employee.");
            console.error(error);
          });
      }
    }
  }

  function validateForm() {
    let valid = true;
    const errorsCopy = { ...errors };

    if (!firstname.trim()) {
      errorsCopy.firstname = "First name is required";
      valid = false;
    } else { errorsCopy.firstname = ""; }

    if (!lastname.trim()) {
      errorsCopy.lastname = "Last name is required";
      valid = false;
    } else { errorsCopy.lastname = ""; }

    if (!email.trim()) {
      errorsCopy.email = "Email is required";
      valid = false;
    } else { errorsCopy.email = ""; }

    if (departmentId && departmentId !== "") {
      errorsCopy.department = '';
    } else {
      errorsCopy.department = 'Please select a department';
      valid = false;
    }

    setErrors(errorsCopy);
    return valid;
  }

  return (
    <div className="container pb-5">
      <div className="row">
        <div className="card col-md-6 offset-md-3 shadow mt-5">
          <h2 className="text-center my-4">{id ? "Update Employee" : "Add Employee"}</h2>
          <div className="card-body">
            <form>
              <div className="form-group mb-2">
                <label className="form-label">First Name:</label>
                <input
                  type="text"
                  placeholder="Enter First Name"
                  value={firstname}
                  className={`form-control ${errors.firstname ? "is-invalid" : ""}`}
                  onChange={(e) => setFirstName(e.target.value)}
                />
                {errors.firstname && <div className="invalid-feedback">{errors.firstname}</div>}
              </div>

              <div className="form-group mb-2">
                <label className="form-label">Last Name:</label>
                <input
                  type="text"
                  placeholder="Enter Last Name"
                  value={lastname}
                  className={`form-control ${errors.lastname ? "is-invalid" : ""}`}
                  onChange={(e) => setLastName(e.target.value)}
                />
                {errors.lastname && <div className="invalid-feedback">{errors.lastname}</div>}
              </div>

              <div className="form-group mb-2">
                <label className="form-label">Email:</label>
                <input
                  type="email"
                  placeholder="Enter Email"
                  value={email}
                  className={`form-control ${errors.email ? "is-invalid" : ""}`}
                  onChange={(e) => setEmail(e.target.value)}
                />
                {errors.email && <div className="invalid-feedback">{errors.email}</div>}
              </div>

              <div className="form-group mb-2">
                <label className="form-label">Select Department:</label>
                <select
                  className={`form-control ${errors.department ? "is-invalid" : ""}`}
                  value={departmentId}
                  onChange={(e) => setDepartmentId(e.target.value)}
                >
                  <option value="">Select Department</option>
                  {departments.map(dept => (
                    <option key={dept.id} value={dept.id}>{dept.departmentName}</option>
                  ))}
                </select>
                {errors.department && <div className="invalid-feedback">{errors.department}</div>}
              </div>

              <div className="form-group mb-2">
                <label className="form-label">Assigned Task:</label>
                <textarea
                  placeholder="Describe the task..."
                  value={assignedTask}
                  className="form-control"
                  onChange={(e) => setAssignedTask(e.target.value)}
                />
              </div>

              <div className="form-group mb-2">
                <label className="form-label">Deadline:</label>
                <input
                  type="date"
                  value={deadline}
                  className="form-control"
                  onChange={(e) => setDeadline(e.target.value)}
                />
              </div>

              <div className="form-group mb-4">
                <label className="form-label">Task Status:</label>
                <select
                  className="form-control"
                  value={taskStatus}
                  onChange={(e) => setTaskStatus(e.target.value)}
                >
                  <option value="PENDING">Pending</option>
                  <option value="IN_PROGRESS">In Progress</option>
                  <option value="COMPLETED">Completed</option>
                </select>
              </div>

              <button className="btn btn-success w-100" onClick={saveOrUpdateEmployee}>
                {id ? "Update Employee" : "Submit"}
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default EmployeeComponent;
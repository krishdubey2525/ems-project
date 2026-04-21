
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import './App.css'
import EmployeeComponent from './components/EmployeeComponent'
import FooterComponent from './components/FooterComponent'
import HeaderComponent from './components/HeaderComponent'
import ListDepartmentComponent from './components/ListDepartmentComponent'
import ListEmployeesComponent from './components/ListEmployeesComponent'
import { DepartmentComponent } from './components/DepartmentComponent'
import { Toaster } from 'react-hot-toast'



function App() {
  
  return (
    <>
    <Toaster position="top-center" reverseOrder={false} />
    
    <BrowserRouter>
    <HeaderComponent/>

    <Routes>

      {/* //http://localhost:3000 */}
      <Route path='/' element={<ListEmployeesComponent />}>  </Route>

      {/* //http://localhost:3000/employees */}
      <Route path ='/employees' element={<ListEmployeesComponent />} ></Route>

      {/* //http://localhost:3000/add-employee */}
      <Route path='/add-employee' element={<EmployeeComponent />}></Route>
 
      {/* //http://localhost:3000/add-employee/1 */}
      <Route path='/edit-employee/:id' element={<EmployeeComponent />}></Route>

       {/* //http://localhost:3000/departments */}
      <Route path='/departments' element={<ListDepartmentComponent />}></Route>
      
      {/* //http://localhost:3000/add-department */}
      <Route path='/add-department' element={<DepartmentComponent/>}></Route>

      <Route path='/edit-department/:id' element={<DepartmentComponent/>}></Route>

    </Routes>
       <FooterComponent/>
    </BrowserRouter>
    
    </>
  )
}
 
export default App

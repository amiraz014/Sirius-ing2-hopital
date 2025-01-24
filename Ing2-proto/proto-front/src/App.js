import {createBrowserRouter, RouterProvider } from 'react-router-dom';
import RootLayout from './Pages/RootLayout/RootLayout';
import HomePage from './Pages/Home/HomePage';
import FormPage from './Pages/Form/FormPage';
import UserPage from './Pages/User/UserPage';
import ManagePage from './Pages/EmployeManage/ManagePage';
import GardFormular from './Pages/EmployeManage/components/GardFormular';
function App() {

  const router = createBrowserRouter([
    {path : '/',
      element : <RootLayout/>,
      children : [
        { path : '/' , element : <HomePage/>},
        {path : '/Manage', element : <ManagePage/>},
        {path : '/list', element: <UserPage/>},
        {path : '/form', element : <GardFormular/>},
        {path : '/form', element : <GardFormular/>}
  ]}
  ]);
  return (
   <>
   <RouterProvider router = {router}/>
   </>
  );
}

export default App;

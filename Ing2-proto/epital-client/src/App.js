import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import RootLayout from './Pages/RootLayout/RootLayout';
import HomePage from './Pages/Home/HomePage';
import ManagePage from './Pages/EmployeManage/ManagePage';
import GardFormular from './Pages/EmployeManage/components/GardFormular';
import EmployeVIew from './Pages/EmployeManage/EmployeVIew';
<<<<<<< HEAD
import AbsenceInterface from './Pages/EmployeManage/components/AbsenceInterface';
import LoginForm from './Pages/EmployeManage/components/LoginForm';
=======
import HistoriqueStock from './Pages/Historique/components/HistoriqueStock';
import AbsenceInterface from './Pages/EmployeManage/components/AbsenceInterface';
>>>>>>> prod
function App() {
  const router = createBrowserRouter([
    {path : '/',
      element : <RootLayout/>,
      children : [
        { path : '/' , element : <HomePage/>},
        {path : '/Manage', element : <ManagePage/>},
        {path : '/form', element : <GardFormular/>},
        {path : '/EmployeView', element : <EmployeVIew/>},
<<<<<<< HEAD
        {path: '/absence', element: <AbsenceInterface/>},
        {path: '/login', element: <LoginForm/>}
=======
        {path: '/historique', element: <HistoriqueStock/> },
        {path: '/absence', element: <AbsenceInterface/>}
>>>>>>> prod
  ]}
  ]);

  return (
      <>
        <RouterProvider router={router} />
      </>
  );
}

export default App;
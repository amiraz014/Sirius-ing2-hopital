import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import RootLayout from './Pages/RootLayout/RootLayout';
import HomePage from './Pages/Home/HomePage';
import ManagePage from './Pages/EmployeManage/ManagePage';
import GardFormular from './Pages/EmployeManage/components/GardFormular';
import EmployeVIew from './Pages/EmployeManage/EmployeVIew';
import AbsenceInterface from './Pages/EmployeManage/components/AbsenceInterface';
import LoginForm from './Pages/EmployeManage/components/LoginForm';
function App() {
  const router = createBrowserRouter([
    {path : '/',
      element : <RootLayout/>,
      children : [
        { path : '/' , element : <HomePage/>},
        {path : '/Manage', element : <ManagePage/>},
        {path : '/form', element : <GardFormular/>},
        {path : '/EmployeView', element : <EmployeVIew/>},
        {path: '/absence', element: <AbsenceInterface/>},
        {path: '/login', element: <LoginForm/>},
        { path: '/stock-management', element: <StockManagement /> },
        { path: '/stock-history', element: <StockHistory /> },
        { path: '/stock-history/medicament', element: <StockHistoryByMedicament /> },
        { path: '/stock-history/medicament/:id', element: <MedicamentDetails /> }
  ]}
  ]);

  return (
      <>
        <RouterProvider router={router} />
      </>
  );
}

export default App;
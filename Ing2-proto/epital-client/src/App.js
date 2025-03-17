import {createBrowserRouter, RouterProvider } from 'react-router-dom';
import RootLayout from './Pages/RootLayout/RootLayout';
import HomePage from './Pages/Home/HomePage';
import UserPage from './Pages/User/UserPage';
import ManagePage from './Pages/EmployeManage/ManagePage';
import GardFormular from './Pages/EmployeManage/components/GardFormular';
import StockSimulation from './Pages/Historique/components/StockSimulation';
import EmployeVIew from './Pages/EmployeManage/EmployeVIew';
import HistoriqueStock from './Pages/Historique/components/HistoriqueStock';
import AbsenceInterface from './Pages/EmployeManage/components/AbsenceInterface';
function App() {

  const router = createBrowserRouter([
    {path : '/',
      element : <RootLayout/>,
      children : [
        { path : '/' , element : <HomePage/>},
        {path : '/Manage', element : <ManagePage/>},
        {path : '/list', element: <UserPage/>},
        {path : '/form', element : <GardFormular/>},
        {path : '/stock', element : <StockSimulation/>},
        {path : '/EmployeView', element : <EmployeVIew/>},
        {path: '/historique', element: <HistoriqueStock/> },
        {path: '/absence', element: <AbsenceInterface/>}
  ]}
  ]);
  return (
   <>
   
   <RouterProvider router = {router}/>
   </>
  );
}

export default App;

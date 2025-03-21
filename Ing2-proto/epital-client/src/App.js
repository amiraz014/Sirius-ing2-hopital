import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import RootLayout from './Pages/RootLayout/RootLayout';
import HomePage from './Pages/Home/HomePage';
import UserPage from './Pages/User/UserPage';
import ManagePage from './Pages/EmployeManage/ManagePage';
import GardFormular from './Pages/EmployeManage/components/GardFormular';
import EmployeVIew from './Pages/EmployeManage/EmployeVIew';
import StockManagement from './Pages/StockManagement/StockManagement';
import StockHistory from './Pages/StockHistory/StockHistory';
import StockHistoryByMedicament from './Pages/StockHistory/StockHistoryByMedicament';
import MedicamentDetails from './Pages/StockHistory/MedicamentDetails';

function App() {
  const router = createBrowserRouter([
    {
      path: '/',
      element: <RootLayout />,
      children: [
        { path: '/', element: <HomePage /> },
        { path: '/Manage', element: <ManagePage /> },
        { path: '/list', element: <UserPage /> },
        { path: '/form', element: <GardFormular /> },

        { path: '/EmployeView', element: <EmployeVIew /> },

        { path: '/stock-management', element: <StockManagement /> },
        { path: '/stock-history', element: <StockHistory /> },
        { path: '/stock-history/medicament', element: <StockHistoryByMedicament /> },
        { path: '/stock-history/medicament/:id', element: <MedicamentDetails /> },
      ],
    },
  ]);

  return (
      <>
        <RouterProvider router={router} />
      </>
  );
}

export default App;
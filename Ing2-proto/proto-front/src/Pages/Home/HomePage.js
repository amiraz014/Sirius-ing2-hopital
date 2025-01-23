import logo from './Epitalogo.svg';
export default function HomePage(){

    return(
        <>
         <div className="flex flex-col items-center justify-center min-h-screen bg-gray-900 text-white">
      <header className="text-center">
        <img src={logo} alt="logo" className="w-40 h-40 flex items-center ml-15 animate-pulse" />
      </header>
    </div>
        </>
    );
}

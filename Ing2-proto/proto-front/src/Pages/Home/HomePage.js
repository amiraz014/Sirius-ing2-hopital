import logo from './Epitalogo.svg';
export default function HomePage(){

    return(
        <>
         <div className="flex flex-col items-center justify-center min-h-screen bg-gray-900 text-white">
      <header className="text-center">
        <img src={logo} alt="logo" className="w-40 h-40 flex items-center ml-24 animate-pulse" />
        <p className="ml-8 text-lg italic">
          Bienvenue dans notre application Epital!
        </p>
      </header>
    </div>
        </>
    );
}
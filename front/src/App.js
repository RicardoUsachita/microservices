import logo from './logo.svg';
import './App.css';

function App() {
  return (
    <div>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<HomeComponent />} />
        <Route path= "/subir-archivo" element={<FileUploadComponent />} />
        <Route path= "/informacion-archivo" element={<FileInformationComponent />} />
        <Route path= "/lista-empleados" element={<EmployeeComponent />} />
        <Route path= "/justificativo" element={<JustificativoComponent />} />
        <Route path= "/autorizacion" element={<AutorizacionComponent />} />
        <Route path= "/planilla-sueldos" element={<SueldosComponent />} />

      </Routes>
    </BrowserRouter>
  </div>
  );
}

export default App;

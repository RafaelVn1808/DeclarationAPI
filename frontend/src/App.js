import { useState } from 'react'
import css from  './App.module.css';
import { IoArrowBack } from "react-icons/io5";
import axios from 'axios';

function App() {
  const [clicked, setClicked] = useState(false)
  const [formSelected, setFormSelected] = useState("")
  const [form, setForm] = useState({
    nome: "",
    matricula: "",
    vinculo: "",
    dateInicio: "",
    dateFim: "",
    curso: ""
  })

  const formatDate = (dateStr) => {
    const [year, month, day] = dateStr.split('/');
    return `${day}-${month}-${year}`;
  };

  const dadosFormatados = {
    ...form,
    dateInicio: formatDate(form.dateInicio),
    dateFim: formatDate(form.dateFim)
  };

  function changeClicked(e){
    setClicked(!clicked)
    setFormSelected(e.target.id)
  }

  async function enviarForm(e){
    e.preventDefault()

    try {
      console.log(dadosFormatados)
      const response = await axios.post('http://localhost:8080/api/declaracoes/estagio/pdf', dadosFormatados, {
        responseType: 'blob',
      });

      const blob = new Blob([response.data], { type: 'application/pdf' });
      const url = window.URL.createObjectURL(blob);

      const a = document.createElement('a');
      a.href = url;
      a.download = 'declaracao-estagiario.pdf';
      a.click();
    } catch (error) {
      console.error('Erro ao enviar dados:', error);
      alert('Erro ao gerar PDF. Verifique a API.');
    }
  }

  return (
    <>
      <main>
        
        <div className={css.center}>
          <div className={css.titulo}>
          {clicked ? <><p onClick={changeClicked}><IoArrowBack /></p></> : <></>}
            <h1>Gerador de Declarações</h1>
          </div>
          {clicked ? <>
          <div className={css.formulario}>
            <form onSubmit={enviarForm}>
              <h3>Nome:</h3>
              <input required value={form.nome} onChange={(e)=>{setForm({...form, nome: e.target.value})}} type="text"/>
              <h3>Matricula:</h3>
              <input required value={form.matricula} onChange={(e)=>{setForm({...form, matricula: e.target.value})}} type="text"/>
              <h3>Vinculo:</h3>
              <input required value={form.vinculo} onChange={(e)=>{setForm({...form, vinculo: e.target.value})}} type="text"/>
              <h3>Data de inicio:</h3>
              <input required value={form.dateInicio} type="date" onChange={(e)=>{setForm({...form, dateInicio: e.target.value})}}/>
              <h3>Data de fim:</h3>
              <input required value={form.dateFim} type="date" onChange={(e)=>{setForm({...form, dateFim: e.target.value})}}/>
              {formSelected == "estagio" ? <>
              <h3>Curso:</h3>
              <input required value={form.curso} onChange={(e)=>{setForm({...form, curso: e.target.value})}} type="text"/>
              </> : <></>}
              {formSelected == "temporario" || formSelected == "efetivo" ? <>
              <h3>Cargo:</h3>
              <input required value={form.cargo} onChange={(e)=>{setForm({...form, cargo: e.target.value})}} type="text"/>
              </> : <></>}
              {formSelected == "efetivo" ? <>
              <h3>Número de Portaria:</h3>
              <input required value={form.numberPort} onChange={(e)=>{setForm({...form, numberPort: e.target.value})}} type="text"/>
              <h3>Data Portaria ou Decreto:</h3>
              <input required value={form.numberPtDt} type="date" onChange={(e)=>{setForm({...form, numberPtDt: e.target.value})}}/>
              <h3>Data D.O.E:</h3>
              <input required value={form.dataDoe} type="date" onChange={(e)=>{setForm({...form, dataDoe: e.target.value})}}/>
              <h3>Número D.O.E:</h3>
              <input required value={form.numberDoe} onChange={(e)=>{setForm({...form, numberDoe: e.target.value})}} type="text"/>
              <h3>Posse:</h3>
              <input required value={form.posse} onChange={(e)=>{setForm({...form, posse: e.target.value})}} type="text"/>
              </> : <></>}
              <input type="submit" value="Enviar" />
            </form>
          </div>
          </> : <>
          <div className={css.options__declaracao}>
            <div id='estagio' onClick={(e)=>changeClicked(e)} className={css.option__declaracao}>
              <h2>Declaração de Estagio</h2>
            </div>
            <div id='temporario' onClick={(e)=>changeClicked(e)} className={css.option__declaracao}>
              <h2>Declaração de Temporario</h2>
            </div>
            <div id="efetivo" onClick={(e)=>changeClicked(e)} className={css.option__declaracao}>
              <h2>Declaração de Efetivo</h2>
            </div>
          </div>
          </>}

        </div>
        
      </main>
    </>
  )
}

export default App


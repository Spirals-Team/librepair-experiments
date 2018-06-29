import { Component } from '@angular/core';
import { BaseCrudComponent } from '../base/base-crud.component';
import { routerTransition } from '../router.animations';
import { Validators } from '@angular/forms';
import { SomenteNumeros, requiredMinLength } from '../utils/validators.utils.component';
import { Aluno } from '../model/aluno.model';

@Component({
  selector: 'app-aluno',
  templateUrl: './aluno.component.html',
  styleUrls: ['./aluno.component.scss'],
  animations: [routerTransition()]
})
export class AlunoComponent extends BaseCrudComponent {
  validacaoCustomizada: any;
  rota= 'aluno';  
  formulario = this.construtorFormulario.group({
    cadastro: this.construtorFormulario.group({
      id: [null],
      nome: [null, [Validators.required, Validators.minLength(5)]],
      matricula: [null, [Validators.required, SomenteNumeros]],
      email: [null, [Validators.required, Validators.email]],
      senha: [null, [Validators.required, Validators.minLength(5)]],
      confirmarSenha: [null, [Validators.required]],
      dataNascimento: [null, [Validators.required]],
      sexo: [null, [Validators.required]],
      curso: [null],
      turma: [null],
      instituicao: [null]
    }),
    endereco: this.construtorFormulario.group({
      id:[null],
      estado: [null,[Validators.required]],
      municipio: [null,[Validators.required]],
      cep:[null,[Validators.required, requiredMinLength(8, true)]],
      complemento: [null,[Validators.required, Validators.maxLength(255)]],
      logradouro:[null,[Validators.required, Validators.maxLength(255)]],
      bairro: [null,[Validators.required, Validators.maxLength(255)]]
    })
  });

  multiValidacao = 
  {
    formulario: this.formulario.controls.cadastro,
    eValido: false
  };
 
  finalizar(){
    if(this.formulario.valid){
      let aluno = new Aluno(this.formulario.value.cadastro);
      aluno.adicionarEndereco(this.formulario.controls.endereco.value, this.LimparCaracterEspecial(this.formulario.controls.endereco.value.cep));
      this.Persistir(aluno);
    }else{
      this.TocarTodos(this.formulario);
  }
  }
}

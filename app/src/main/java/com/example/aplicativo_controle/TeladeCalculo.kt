package com.example.aplicativo_controle

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.aplicativo_controle.databinding.ActivityTeladeCalculoBinding

class TeladeCalculo : AppCompatActivity() {

    // Variável de binding para acessar os elementos do layout
    private lateinit var binding: ActivityTeladeCalculoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_telade_calculo)

        // Inicializando o binding
        binding = ActivityTeladeCalculoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurando o evento de clique no botão
        binding.btnCalcular.setOnClickListener {
            val nome = binding.etNome.text.toString()
            val pesoText = binding.etPeso.text.toString()
            val alturaText = binding.etAltura.text.toString()

            // Validação dos campos
            if (nome.isEmpty() || pesoText.isEmpty() || alturaText.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val peso = pesoText.toDoubleOrNull()
            val altura = alturaText.toDoubleOrNull()

            if (peso == null || altura == null || altura <= 0) {
                Toast.makeText(this, "Valores inválidos!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Criar objetos e calcular IMC
            val pessoa = Pessoa(nome, peso, altura)
            val imcCalculator = IMC()
            val classificacaoIMC = ClassificacaoIMC()

            val imc = imcCalculator.calcularIMC(pessoa.peso, pessoa.altura)
            val classificacao = classificacaoIMC.obterClassificacao(imc)

            // Mostrar o resultado
            val resultado = "Olá, ${pessoa.nome}!\n" +
                    "Seu IMC é: %.2f\n".format(imc) +
                    "Classificação: $classificacao"
            binding.tvResultado.text = resultado
        }
    }
}
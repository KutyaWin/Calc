import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.service.CalculatorApiService;
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val apiService = CalculatorApiService.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener {
            val num1 = binding.number1Input.text.toString()
            val num2 = binding.number2Input.text.toString()
            val operation = binding.operationSpinner.selectedItem.toString()

            if (num1.isBlank() || num2.isBlank()) {
                showError("Введите оба числа")
                return@setOnClickListener
            }

            calculate(num1, operation, num2)
        }
    }

    private fun calculate(num1: String, operation: String, num2: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val expression = "$num1 $operation $num2"
                val result = apiService.calculate(num1, operation, num2)

                withContext(Dispatchers.Main) {
                    binding.resultText.text = "Результат: $result"
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showError("Ошибка: ${e.message}")
                }
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
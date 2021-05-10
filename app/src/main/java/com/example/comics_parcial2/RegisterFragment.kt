package com.example.comics_parcial2

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        loginText.setOnClickListener {
//            fragmentManager?.popBackStackImmediate()
//        }
        loginText.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_registerFragment_to_loginFragment)
        )
        setupRegister()
    }

    private fun setupRegister() {
        registerButton.setOnClickListener {
            if (registerEmailText.text.isNotEmpty() && registerPwdText.text.isNotEmpty()) {
                if (registerPwdText.text.toString() == registerPwdText2.text.toString()) {
                    if (TCCheckbox.isChecked) {
                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                            registerEmailText.text.toString(),
                            registerPwdText.text.toString()
                        ).addOnCompleteListener {
                            if (it.isSuccessful) {
                                usuarioCreado()
                                registerEmailText.text.clear()
                                registerPwdText.text.clear()
                                registerPwdText2.text.clear()
                            }
                        }.addOnFailureListener {
                            Toast.makeText(context, it.toString(), Toast.LENGTH_LONG).show()
                        }
                    }
                    else{
                        Toast.makeText(context, "You need to agree to the Terms and Conditions.", Toast.LENGTH_LONG).show()
                    }
                }
                else{
                    Toast.makeText(context,
                        "Passwords don't match.", Toast.LENGTH_LONG).show()
                }
            }
            else{
                Toast.makeText(context,"Mail and password cannot be empty.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun usuarioCreado(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("¡Nuevo usuario creado con éxito!")
            .setCancelable(false)
            .setPositiveButton("Ok", DialogInterface.OnClickListener{
                //dialog, which -> loginButton.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                dialog, id -> finish()
            })
        val alert = builder.create()
        alert.setTitle("Registro exitoso")
        alert.show()
    }

    private fun finish(){
        view?.findNavController()?.navigate(R.id.action_registerFragment_to_loginFragment)
    }
}
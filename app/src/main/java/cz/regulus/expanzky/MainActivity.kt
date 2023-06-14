package cz.regulus.expanzky

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import cz.regulus.expanzky.Tables.kolektory
import cz.regulus.expanzky.Tables.teplaVoda
import cz.regulus.expanzky.Tables.topeni
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.max

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        // hide everything

        tvSol.text = getString(R.string.en_sol, "")
        tvTop.text = getString(R.string.en_top, "")
        tvTv.text = getString(R.string.en_tv, "")
        clSolMore.visibility = GONE
        clTopMore.visibility = GONE
        clTvMore.visibility = GONE

        // showing on click

        cvSol.setOnClickListener {
            when(clSolMore.visibility) {
                GONE, INVISIBLE -> {
                    clSolMore.visibility = VISIBLE
                    tvSol.text = getString(R.string.en_sol, ":")
                    clTopMore.visibility = GONE
                    tvTop.text = getString(R.string.en_top, "")
                    clTvMore.visibility = GONE
                    tvTv.text = getString(R.string.en_tv, "")
                }
                VISIBLE -> {
                    clSolMore.visibility = GONE
                    tvSol.text = getString(R.string.en_sol, "")
                }
            }

        }
        cvTop.setOnClickListener {
            when(clTopMore.visibility) {
                GONE, INVISIBLE -> {
                    clTopMore.visibility = VISIBLE
                    tvTop.text = getString(R.string.en_top, ":")
                    clTvMore.visibility = GONE
                    tvTv.text = getString(R.string.en_tv, "")
                    clSolMore.visibility = GONE
                    tvSol.text = getString(R.string.en_sol, "")
                }
                VISIBLE -> {
                    clTopMore.visibility = GONE
                    tvTop.text = getString(R.string.en_top, "")
                }
            }
        }
        cvTv.setOnClickListener {
            when(clTvMore.visibility) {
                GONE, INVISIBLE -> {
                    clTvMore.visibility = VISIBLE
                    tvTv.text = getString(R.string.en_tv, ":")
                    clTopMore.visibility = GONE
                    tvTop.text = getString(R.string.en_top, "")
                    clSolMore.visibility = GONE
                    tvSol.text = getString(R.string.en_sol, "")
                }
                VISIBLE -> {
                    clTvMore.visibility = GONE
                    tvTv.text = getString(R.string.en_tv, "")
                }
            }
        }

        // seting up spinners

        spSolKolektorTyp.adapter = ArrayAdapter.createFromResource(this, R.array.kolektory, android.R.layout.simple_spinner_dropdown_item)
        spSolPotrubiTyp.adapter = ArrayAdapter.createFromResource(this, R.array.trubky, android.R.layout.simple_spinner_dropdown_item)
        spSolPotrubiStrechaTyp.adapter = ArrayAdapter.createFromResource(this, R.array.trubky, android.R.layout.simple_spinner_dropdown_item)

        // updating tv

        etTvObjem.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                try {
                    aktualizovatTv(s.toString().toFloat())
                } catch (e: NumberFormatException) {
                    aktualizovatTv(0F)
                }

            }

        })

        // updating top

        val w = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                try {
                    aktualizovatTop(
                        etTopTeplota.text.toString().toFloat(),
                        etTopPrevyseni.text.toString().toFloat(),
                        etTopTlakKotel.text.toString().toFloat(),
                        etTopObjem.text.toString().toFloat(),
                        etTopTlak.text.toString().toFloat()
                    )
                } catch (e: NumberFormatException) {
                    aktualizovatTop(0F, 0F, 0F, 0F, 0F)
                }

            }

        }

        etTopTeplota.addTextChangedListener(w)
        etTopPrevyseni.addTextChangedListener(w)
        etTopTlakKotel.addTextChangedListener(w)
        etTopObjem.addTextChangedListener(w)
        etTopTlak.addTextChangedListener(w)

        // updating sol

        val w2 = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                try {
                    aktualizovatSol(
                        etSolH.text.toString().toFloat(),
                        etSolVymenikObjem.text.toString().toFloat(),
                        spSolKolektorTyp.selectedItem.toString(),
                        spSolKolektorTyp.selectedItemPosition,
                        spSolPotrubiTyp.selectedItemPosition,
                        spSolPotrubiStrechaTyp.selectedItemPosition,
                        etSolKolektorPocet.text.toString().toInt(),
                        etSolPotrubiDelka.text.toString().toFloat(),
                        etSolPotrubiStrechaDelka.text.toString().toFloat()
                    )
                } catch (e: NumberFormatException) {
                    aktualizovatSol(0F, 0F, "", 0, 0, 0, 0, 0F, 0F)
                }

            }

        }

        val l = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                try {
                    aktualizovatSol(
                        etSolH.text.toString().toFloat(),
                        etSolVymenikObjem.text.toString().toFloat(),
                        spSolKolektorTyp.selectedItem.toString(),
                        spSolKolektorTyp.selectedItemPosition,
                        spSolPotrubiTyp.selectedItemPosition,
                        spSolPotrubiStrechaTyp.selectedItemPosition,
                        etSolKolektorPocet.text.toString().toInt(),
                        etSolPotrubiDelka.text.toString().toFloat(),
                        etSolPotrubiStrechaDelka.text.toString().toFloat()
                    )
                } catch (e: NumberFormatException) {
                    aktualizovatSol(0F, 0F, "", 0, 0, 0, 0, 0F, 0F)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        etSolH.addTextChangedListener(w2)
        etSolKolektorPocet.addTextChangedListener(w2)
        etSolPotrubiDelka.addTextChangedListener(w2)
        etSolPotrubiStrechaDelka.addTextChangedListener(w2)
        etSolVymenikObjem.addTextChangedListener(w2)

        spSolKolektorTyp.onItemSelectedListener = l
        spSolPotrubiTyp.onItemSelectedListener = l
        spSolPotrubiStrechaTyp.onItemSelectedListener = l

        // starting

        aktualizovatTv(0F)
        aktualizovatSol(0F, 0F, "", 0, 0, 0, 0, 0F, 0F)
        aktualizovatTop(0F, 0F, 0F, 0F, 0F)

        // hints

        tvSolH.setOnClickListener {
            val img = ImageView(this)
            img.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.schema054))


            AlertDialog.Builder(this).apply {

                setTitle(R.string.vysvetleni)

                setView(img)

                setNeutralButton(android.R.string.ok) { dialog, _ ->
                    dialog.cancel()
                }


                show()
            }

        }

        tvSolVymenikObjem.setOnClickListener {

            AlertDialog.Builder(this).apply {

                setTitle(R.string.dopo_hodn)

                setMessage(
                    "RDC250, RDC300 — 10\nDUO 390/130 PR, HSK 390 PR — 9"
                )

                setNeutralButton(android.R.string.ok) { dialog, _ ->
                    dialog.cancel()
                }


                show()
            }

        }

        tvTopPrevyseni.setOnClickListener {
            val img = ImageView(this)
            img.setImageDrawable(ContextCompat.getDrawable(this@MainActivity, R.drawable.schema053))


            AlertDialog.Builder(this).apply {

                setTitle(R.string.vysvetleni)

                setView(img)

                setNeutralButton(android.R.string.ok) { dialog, _ ->
                    dialog.cancel()
                }


                show()
            }

        }

        tvSolPotrubiStrechaTyp.setOnClickListener {

            AlertDialog.Builder(this).apply {

                setTitle(R.string.vysvetleni)

                setMessage(R.string.stresni_potrubi_vysl)

                setNeutralButton(android.R.string.ok) { dialog, _ ->
                    dialog.cancel()
                }


                show()
            }
        }

        tvTopTlak.setOnClickListener {

            AlertDialog.Builder(this).apply {

                setTitle(R.string.vysvetleni)

                setMessage(getString(R.string.hodnota_poj_v))

                setNeutralButton(android.R.string.ok) { dialog, _ ->
                    dialog.cancel()
                }


                show()
            }
        }

        tvTopTlakKotel.setOnClickListener {

            AlertDialog.Builder(this).apply {

                setTitle(R.string.vysvetleni)

                setMessage(getString(R.string.urceno_vyrobcem_kotle))

                setNeutralButton(android.R.string.ok) { dialog, _ ->
                    dialog.cancel()
                }


                show()
            }
        }

    }


    fun aktualizovatTv(vZasobniku: Float) {
        val v = vZasobniku * .04F

        var nadrz: List<String>? = null
        for ((V, exp) in teplaVoda) {
            if (v == 0F) {
                nadrz = listOf("", "", "")
                break
            }
            if (V >= v) {
                nadrz = exp
                break
            }
        }
        if (nadrz == null) {
            nadrz = listOf("", "", getString(R.string.neni_v_nabidce))
        }


        tvTvKod.text = nadrz[0]
        tvTvZkratka.text = nadrz[1]
        tvTvNazev.text = nadrz[2]
    }


    fun aktualizovatSol(h: Float, vVymenik: Float, typKolektoru: String, typKolektoruPos: Int, typTrubekPos: Int, typTrubekStrechaPos: Int, pocetKolektoru: Int, delkaTrubek: Float, delkaTrubekStrecha: Float) {
        val pMaximalni = 6F
        val pPlnici = h / 10F + 1.3F

        val vRoztaznost = if (typKolektoru.contains("KP"))
            .09F
        else
            .12F

        val vKolektoru = resources.getStringArray(R.array.kolektory_merny_objem).toList()[typKolektoruPos].toFloat() * pocetKolektoru
        val vTrubek = resources.getStringArray(R.array.trubky_merny_objem).toList()[typTrubekPos].toFloat() * delkaTrubek
        val vTrubekStrecha = resources.getStringArray(R.array.trubky_merny_objem).toList()[typTrubekStrechaPos].toFloat() * delkaTrubekStrecha

        val vKapaliny = vKolektoru + vTrubek + vTrubekStrecha + vVymenik
        val vKapalinyStudena = .05F * vKapaliny

        val roztaznost = vRoztaznost * vKapaliny + vKolektoru + vTrubekStrecha + vKapalinyStudena

        val v = roztaznost * (pMaximalni + 1) / (pMaximalni - pPlnici)

        var nadrz: List<String>? = null
        for ((V, exp) in kolektory) {
            if (v == 0F) {
                nadrz = listOf("", "", "")
                break
            }
            if (V >= v) {
                nadrz = exp
                break
            }
        }
        if (nadrz == null) {
            nadrz = listOf("", "", getString(R.string.neni_v_nabidce))
        }


        tvSolKod.text = nadrz[0]
        tvSolZkratka.text = nadrz[1]
        tvSolNazev.text = nadrz[2]
    }


    fun aktualizovatTop(t: Float, h: Float, pKotel: Float, vSoustavy: Float, pMaximalni: Float) {
        val deltaV = 1000F / (1000F - (t - 4F) * (.097F + .0036F * (t - 4F))) -
                     1000F / (1000F - 6F       * (.097F + .0036F * 6F      ))

        val pMinimalny = max(h / 10F, pKotel) + .2F

        val v = 1.3F * vSoustavy * deltaV * (pMaximalni + 1F) / (pMaximalni - pMinimalny)

        var nadrz: List<String>? = null
        for ((V, exp) in topeni) {
            if (v <= 0F) {
                nadrz = listOf("", "", "")
                break
            }
            if (V >= v) {
                nadrz = exp
                break
            }
        }
        if (nadrz == null) {
            nadrz = listOf("", "", getString(R.string.neni_v_nabidce))
        }


        tvTopKod.text = nadrz[0]
        tvTopZkratka.text = nadrz[1]
        tvTopNazev.text = nadrz[2]
    }
}
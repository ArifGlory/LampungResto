package irwan.lampungresto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProfilRestoActivity extends AppCompatActivity {

    TextView txtSejarah,txtAlamat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_resto);

        txtSejarah = (TextView) findViewById(R.id.tvNumber);
        txtAlamat = (TextView) findViewById(R.id.tvNumber5);
        txtSejarah.setText("Cikwo Resto adalah salah satu restoran yang menyajikan masakan kuliner khas lampung" +
                " yang berdiri sejak februari 2015. Cikwo Resto dibuka dengan alasan sebagai jawaban atas sulitnya " +
                "bagi masyarakat untuk menemukan masakan khas lampung di Bandarlampung Oleh Isna Adianti. ");
        txtAlamat.setText("Jl Kimaja (Kimaja Icon), samping Jaya Bakery," +
                " 10m sebelum fly over TanjungSeneng, Sepang Jaya, " +
                "Labuhan Ratu, Sepang Jaya, Kedaton, " +
                "Kota Bandar Lampung, Lampung 35213");
    }
}

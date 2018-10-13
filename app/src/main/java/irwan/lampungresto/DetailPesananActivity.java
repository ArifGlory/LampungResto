package irwan.lampungresto;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import irwan.lampungresto.Adapter.RecycleAdapterDetailPesanan;
import irwan.lampungresto.Adapter.RecycleAdapterListPesanan;

public class DetailPesananActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    public static ProgressBar progressBar;
    DatabaseReference ref;
    private FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener fStateListener;
    Intent i;
    public static String keyPembeli,keyOrder,namaPembeli,phone,status;
    public static TextView txtNamaPembeli,txtPhone,txtStatus;
    RecycleAdapterDetailPesanan adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesanan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Detail Pesanan");
        initCollapsingToolbar();

        i = getIntent();
        keyPembeli = i.getStringExtra("keyPembeli");
        keyOrder = i.getStringExtra("keyOrder");
        status = i.getStringExtra("status");

        Firebase.setAndroidContext(this);
        FirebaseApp.initializeApp(DetailPesananActivity.this);
        ref = FirebaseDatabase.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        txtNamaPembeli = (TextView) findViewById(R.id.txtNamaPembeli);
        txtPhone = (TextView) findViewById(R.id.txtPhone);
        txtStatus = (TextView) findViewById(R.id.txtStatus);
        txtNamaPembeli.setText("Pembeli : ");
        txtPhone.setText("No. HP : ");

        if (status.equals("1")){
            txtStatus.setText("Menunggu Konfirmasi");
        }else if (status.equals("2")){
            txtStatus.setText("Dikonfirmasi , sedang dikirim");
        }else if (status.equals("3")){
            txtStatus.setText("Ditolak");
        }

        adapter = new RecycleAdapterDetailPesanan(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ref.child("pembeli").child(keyPembeli).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                namaPembeli = dataSnapshot.child("displayName").getValue().toString();
                phone = dataSnapshot.child("phone").getValue().toString();

                txtNamaPembeli.setText("Nama : "+namaPembeli);
                txtPhone.setText("HP : "+phone);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }
}

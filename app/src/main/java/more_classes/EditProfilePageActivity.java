package more_classes;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.flyshippment_project.R;

public class EditProfilePageActivity  extends AppCompatActivity
{
    private void arrow_back_function() {
        Intent intent = new Intent(EditProfilePageActivity.this, ProfilePageActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_page);
    }

}

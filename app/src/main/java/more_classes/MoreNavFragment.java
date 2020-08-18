package more_classes;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.flyshippment_project.R;
import com.example.flyshippment_project.Repository;


public class MoreNavFragment extends Fragment {

    public MoreNavFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_more_nav, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button go_to_profile_btn = (Button) view.findViewById(R.id.more_goto_prifle_button);
        ImageView profile_image = (ImageView) view.findViewById(R.id.more_profile_image);
        TextView termsPrivacyText=(TextView)view.findViewById(R.id.terms_privacy_textview);
        TextView userNameText=(TextView)view.findViewById(R.id.user_name_text);

        termsPrivacyText.setMovementMethod(new ScrollingMovementMethod());

        userNameText.setText(Repository.TheProfileItem.getUser_name());

        profile_image.setForeground(null);
        Glide.with(this).load(Repository.TheProfileItem.getUser_image_url())
                .placeholder(R.drawable.round_error_black_18dp)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(profile_image);

        go_to_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getContext(),ProfilePageActivity.class);
                startActivity(intent);
            }
        });
    }
}

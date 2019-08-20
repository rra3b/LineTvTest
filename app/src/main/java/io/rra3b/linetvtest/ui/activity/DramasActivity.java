package io.rra3b.linetvtest.ui.activity;

import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import io.rra3b.linetvtest.InjectorUtils;
import io.rra3b.linetvtest.R;
import io.rra3b.linetvtest.ui.base.BaseActivity;
import io.rra3b.linetvtest.ui.fragment.DramasFragment;
import io.rra3b.linetvtest.ui.fragment.SearchFragment;
import io.rra3b.linetvtest.ui.viewmodel.DramasViewModel;
import io.rra3b.linetvtest.ui.viewmodel.DramasViewModel.FragmentAction;

public class DramasActivity extends BaseActivity {

  private DramasViewModel mViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dramas);

    mViewModel = InjectorUtils.provideDramasViewModel(this);
    mViewModel.getLiveFragmentAction()
        .observe(this, this::handleFragmentAction);

    getSupportFragmentManager().beginTransaction()
        .replace(R.id.a_dramas_flContainer, DramasFragment.newInstance(), DramasFragment.TAG)
        .commit();
  }

  private void handleFragmentAction(@FragmentAction int action) {
    switch (action) {
      case DramasViewModel.FRAGMENT_ACTION_BACK_TO_LIST:
        getSupportFragmentManager()
            .popBackStack(SearchFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        break;
      case DramasViewModel.FRAGMENT_ACTION_SEARCH:
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.a_dramas_flContainer, SearchFragment.newInstance(), SearchFragment.TAG)
            .addToBackStack(SearchFragment.TAG)
            .commit();
        break;
      default:
        break;
    }
  }

  @Override
  public void onBackPressed() {
    if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
      getSupportFragmentManager()
          .popBackStack(SearchFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    } else {
      super.onBackPressed();
    }
  }
}

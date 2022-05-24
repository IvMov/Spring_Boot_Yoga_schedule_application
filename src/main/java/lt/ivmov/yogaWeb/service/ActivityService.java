package lt.ivmov.yogaWeb.service;

import lt.ivmov.yogaWeb.entity.Activity;
import lt.ivmov.yogaWeb.entity.User;
import lt.ivmov.yogaWeb.repository.ActivityRepository;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public Activity update(Activity activity) {
        return activityRepository.save(activity);
    }
}

import http from 'k6/http';
import { check, sleep } from 'k6';

const url = 'http://localhost:8080/process';

export const options = {
    stages: [
        { duration: '2s', target: 30 },
        { duration: '15s', target: 30 },
        { duration: '2s', target: 0 },
    ]
};

export default () => {
    const payload = 'Ian';

    const urlRes = http.post(url, payload, {
        headers: { 'Content-Type': 'text/plain' }
    });

    check(urlRes, {
        'accepted (status 204)': (r) => r.status === 204,
        'rate limited (status 429)': (r) => r.status === 429,
    });

    sleep(0.1);
};
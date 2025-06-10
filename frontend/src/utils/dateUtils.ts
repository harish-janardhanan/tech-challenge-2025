/**
 * Date utilities for trade operations
 */

/**
 * Get today's date in yyyy-mm-dd format
 * @returns {string} Today's date as yyyy-mm-dd
 */
export const getToday = (): string => {
    const d = new Date();
    return d.toISOString().slice(0, 10);
};

/**
 * Get a date one year from today in yyyy-mm-dd format
 * @returns {string} Date one year from today as yyyy-mm-dd
 */
export const getOneYearFromToday = (): string => {
    const d = new Date();
    d.setFullYear(d.getFullYear() + 1);
    return d.toISOString().slice(0, 10);
};

/**
 * Format a date for backend API (ISO8601 with time)
 * @param d - Date to format
 * @returns {string|null} Formatted date string or null
 */
export const formatDateForBackend = (d: string | Date | undefined | null): string | null => {
    if (!d) return null;
    if (typeof d === 'string' && d.includes('T')) return d;
    if (typeof d === 'string') return d + 'T00:00:00';
    if (typeof d === 'object') return d.toISOString();
    return d as string;
};
